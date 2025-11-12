package com.example.grocerystore.persistence.services;

import com.example.grocery.model.discounts.BeerDiscount;
import com.example.grocery.model.discounts.BreadDiscount;
import com.example.grocery.model.discounts.VegetableDiscount;
import com.example.grocerystore.mappers.DbModelMapper;
import com.example.grocerystore.persistence.repos.discounts.BeerDiscountsRepo;
import com.example.grocerystore.persistence.repos.discounts.BreadDiscountsRepo;
import com.example.grocerystore.persistence.repos.discounts.VegetableDiscountsRepo;
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
