package com.example.grocerystore.persistence.services;

import com.example.grocery.model.prices.ProductPriceWithId;
import com.example.grocerystore.mappers.DbModelMapper;
import com.example.grocerystore.persistence.dbmodels.prices.PriceDbModel;
import com.example.grocerystore.persistence.repos.PricesRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PricesPersistenceService {

    private final PricesRepo pricesRepo;
    private final DbModelMapper mapper;

    public List<ProductPriceWithId> retrieveAllPrices() {
        return pricesRepo
                .findAll()
                .stream()
                .map(mapper::toProductPrice)
                .toList();
    }

    public void updatePrice(ProductPriceWithId newPrice) {
        PriceDbModel newPriceDbModel = mapper.toPriceDbModel(newPrice);
        pricesRepo.save(newPriceDbModel);
    }
}
