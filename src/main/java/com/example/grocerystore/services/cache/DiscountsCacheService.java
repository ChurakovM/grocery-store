package com.example.grocerystore.services.cache;

import com.example.grocery.model.discounts.BeerDiscount;
import com.example.grocery.model.discounts.BreadDiscount;
import com.example.grocery.model.discounts.VegetableDiscount;
import com.example.grocerystore.persistence.services.DiscountsPersistenceService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountsCacheService {

    private final DiscountsPersistenceService discountDbService;

    private List<VegetableDiscount> vegetables;
    private List<BeerDiscount> beers;
    private List<BreadDiscount> breads;

    @PostConstruct
    public void loadCache() {
        vegetables = discountDbService.retrieveAllVegetableDiscounts();
        beers = discountDbService.retrieveAllBeerDiscounts();
        breads = discountDbService.retrieveAllBreadDiscounts();
    }

    public List<VegetableDiscount> getVegetables() {
        if (vegetables == null || vegetables.isEmpty()) {
            vegetables = discountDbService.retrieveAllVegetableDiscounts();
        }
        return vegetables;
    }

    public List<BeerDiscount> getBeers() {
        if (beers == null || beers.isEmpty()) {
            beers = discountDbService.retrieveAllBeerDiscounts();
        }
        return beers;
    }

    public List<BreadDiscount> getBreads() {
        if (breads == null || breads.isEmpty()) {
            breads = discountDbService.retrieveAllBreadDiscounts();
        }
        return breads;
    }

    public synchronized void cleanUpVegetableDiscounts() {
        vegetables.clear();
    }

    public synchronized void cleanUpBeersDiscounts() {
        beers.clear();
    }

    public synchronized void cleanUpBreadsDiscounts() {
        breads.clear();
    }
}
