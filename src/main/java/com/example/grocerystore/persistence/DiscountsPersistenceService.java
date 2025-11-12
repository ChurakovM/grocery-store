package com.example.grocerystore.persistence;

import com.example.grocery.model.discounts.BeerDiscount;
import com.example.grocery.model.discounts.BreadDiscount;
import com.example.grocery.model.discounts.VegetableDiscount;
import com.example.grocerystore.mappings.DbModelMapper;
import com.example.grocerystore.persistence.repos.BeerDiscountsRepo;
import com.example.grocerystore.persistence.repos.BreadDiscountsRepo;
import com.example.grocerystore.persistence.repos.VegetableDiscountsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountsPersistenceService {

    private final VegetableDiscountsRepo vegetableRepo;
    private final DbModelMapper dbModelMapper;
    private final BeerDiscountsRepo beerRepo;
    private final BreadDiscountsRepo breadRepo;

    public List<VegetableDiscount> retrieveAllVegetableDiscounts() {
        return vegetableRepo
                .findAll()
                .stream()
                .map(dbModelMapper::toVegetableDiscount)
                .toList();
    }

    public List<BeerDiscount> retrieveAllBeerDiscounts() {
        return beerRepo
                .findAll()
                .stream()
                .map(dbModelMapper::toBeerDiscount)
                .toList();
    }

    public List<BreadDiscount> retrieveAllBreadDiscounts() {
        return breadRepo
                .findAll()
                .stream()
                .map(dbModelMapper::toBreadDiscount)
                .toList();
    }
}
