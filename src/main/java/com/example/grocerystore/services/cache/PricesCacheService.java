package com.example.grocerystore.services.cache;

import com.example.grocery.model.orders.ProductType;
import com.example.grocery.model.prices.ProductPrice;
import com.example.grocery.model.prices.ProductPriceWithId;
import com.example.grocerystore.mappers.ModelMapper;
import com.example.grocerystore.persistence.services.PricesPersistenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class PricesCacheService {

    private final PricesPersistenceService pricesDbService;
    private final ModelMapper modelMapper;

    private final AtomicReference<List<ProductPriceWithId>> pricesWithIdRef = new AtomicReference<>();
    private final AtomicReference<List<ProductPrice>> pricesRef = new AtomicReference<>();

    @EventListener(ApplicationReadyEvent.class)
    public void loadCache() {
        refreshCache();
    }

    public void refreshCache() {
        List<ProductPriceWithId> dbData = pricesDbService.retrieveAllPrices();
        List<ProductPriceWithId> freshData = List.copyOf(dbData);
        pricesWithIdRef.set(freshData);

        List<ProductPrice> pricesWithoutIds = modelMapper.toListOfProductPrices(freshData);
        pricesRef.set(List.copyOf(pricesWithoutIds));
    }

    public List<ProductPriceWithId> getPricesWIthIds() {
        return pricesWithIdRef.get();
    }

    public List<ProductPrice> getPrices() {
        return pricesRef.get();
    }

    public ProductPrice getPriceByProductType(ProductType productType) {
        return getPrices()
                .stream()
                .filter(price -> price.getProductType().equalsIgnoreCase(productType.getValue()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(
                        "Price not found for product type: " + productType));
    }

    public ProductPriceWithId getPriceById(Long id) {
        return getPricesWIthIds()
                .stream()
                .filter(price -> price.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(
                        "Price not found for id: " + id));
    }

    public void replacePrice(ProductPriceWithId updatedPrice) {
        List<ProductPriceWithId> current = getPricesWIthIds();

        List<ProductPriceWithId> modified = current.stream()
                .map(price -> price.getId().equals(updatedPrice.getId()) ? updatedPrice : price)
                .toList();

        pricesWithIdRef.set(List.copyOf(modified));

        // Also update the ProductPrice (without ID) cache
        List<ProductPrice> pricesWithoutId = modelMapper.toListOfProductPrices(modified);
        pricesRef.set(List.copyOf(pricesWithoutId));
    }

}
