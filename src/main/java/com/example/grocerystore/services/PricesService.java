package com.example.grocerystore.services;

import com.example.grocery.model.prices.GetAllPricesResponse;
import com.example.grocery.model.prices.ProductPrice;
import com.example.grocery.model.prices.ProductPriceWithId;
import com.example.grocerystore.persistence.services.PricesPersistenceService;
import com.example.grocerystore.services.cache.PricesCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PricesService {

    private final PricesCacheService cacheService;
    private final PricesPersistenceService persistenceService;

    public GetAllPricesResponse getAllPrices() {
        List<ProductPriceWithId> prices = cacheService.getPricesWIthIds();
        return new GetAllPricesResponse().prices(prices);
    }

    public ProductPriceWithId updatePrice(Long id, ProductPrice productPrice) {
        ProductPriceWithId newPrice = cacheService.getPriceById(id);

        newPrice.setUnitPrice(productPrice.getUnitPrice());
        newPrice.setProductType(productPrice.getProductType());
        newPrice.setUnit(productPrice.getUnit());

        persistenceService.updatePrice(newPrice);
        cacheService.replacePrice(newPrice);

        return newPrice;
    }
}
