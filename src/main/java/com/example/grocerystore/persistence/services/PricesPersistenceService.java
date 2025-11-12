package com.example.grocerystore.persistence.services;

import com.example.grocery.model.prices.ProductPrice;
import com.example.grocerystore.mappers.DbModelMapper;
import com.example.grocerystore.persistence.repos.PricesRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PricesPersistenceService {

    private final PricesRepo pricesRepo;
    private final DbModelMapper mapper;

    public List<ProductPrice> retrieveAllPrices() {
        return pricesRepo
                .findAll()
                .stream()
                .map(mapper::toProductPrice)
                .toList();
    }
}
