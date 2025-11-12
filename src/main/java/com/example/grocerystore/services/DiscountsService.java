package com.example.grocerystore.services;

import com.example.grocery.model.discounts.BeerDiscount;
import com.example.grocery.model.discounts.BreadDiscount;
import com.example.grocery.model.discounts.GetAllDiscountsResponse;
import com.example.grocery.model.discounts.VegetableDiscount;
import com.example.grocerystore.persistence.DiscountsPersistenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountsService {

    private final DiscountsPersistenceService discountDbService;

    public GetAllDiscountsResponse getAllDiscounts() {
        List<VegetableDiscount> vegetables = discountDbService.retrieveAllVegetableDiscounts();
        List<BeerDiscount> beers = discountDbService.retrieveAllBeerDiscounts();
        List<BreadDiscount> breads = discountDbService.retrieveAllBreadDiscounts();

        return new GetAllDiscountsResponse()
                .vegetableDiscounts(vegetables)
                .beerDiscounts(beers)
                .breadDiscounts(breads);
    }

}
