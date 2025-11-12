package com.example.grocerystore.services.cache;

import com.example.grocery.model.orders.ProductType;
import com.example.grocery.model.prices.ProductPrice;
import com.example.grocerystore.persistence.services.PricesPersistenceService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PricesCacheService {

    private final PricesPersistenceService pricesDbService;

    private List<ProductPrice> prices;

    @PostConstruct
    public void loadCache() {
        prices = pricesDbService.retrieveAllPrices();
    }

    public List<ProductPrice> getPrices() {
        if (prices == null || prices.isEmpty()) {
            prices = pricesDbService.retrieveAllPrices();
        }
        return prices;
    }

    public ProductPrice getPriceByProductType(ProductType productType) {
        return getPrices()
                .stream()
                .filter(price -> price.getProductType().equalsIgnoreCase(productType.getValue()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(
                        "Price not found for product type: " + productType));
    }

    public synchronized void cleanUpPrices() {
        prices.clear();
    }
}
