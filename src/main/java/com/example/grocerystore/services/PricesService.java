package com.example.grocerystore.services;

import com.example.grocery.model.prices.GetAllPricesResponse;
import com.example.grocery.model.prices.ProductPrice;
import com.example.grocerystore.mappings.DbModelMapper;
import com.example.grocerystore.persistence.repos.PricesRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PricesService {

    private final PricesRepo pricesRepo;
    private final DbModelMapper mapper;

    public GetAllPricesResponse getAllPrices() {
        List<ProductPrice> prices = pricesRepo
                .findAll()
                .stream()
                .map(mapper::toProductPrice)
                .toList();

        return new GetAllPricesResponse()
                .prices(prices);
    }
}
