package com.example.grocerystore.services;

import com.example.grocery.model.prices.GetAllPricesResponse;
import com.example.grocery.model.prices.ProductPrice;
import com.example.grocerystore.services.cache.PricesCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PricesService {

    private final PricesCacheService cacheService;

    public GetAllPricesResponse getAllPrices() {
        List<ProductPrice> prices = cacheService.getPrices();
        return new GetAllPricesResponse().prices(prices);
    }
}
