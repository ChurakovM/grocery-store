package com.example.grocerystore.services;

import com.example.grocery.model.discounts.BeerDiscount;
import com.example.grocery.model.discounts.BreadDiscount;
import com.example.grocery.model.discounts.GetAllDiscountsResponse;
import com.example.grocery.model.discounts.VegetableDiscount;
import com.example.grocerystore.services.cache.DiscountsCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountsService {

    private final DiscountsCacheService cacheService;

    public GetAllDiscountsResponse getAllDiscounts() {
        List<VegetableDiscount> vegetables = cacheService.getVegetables();
        List<BeerDiscount> beers = cacheService.getBeers();
        List<BreadDiscount> breads = cacheService.getBreads();

        return new GetAllDiscountsResponse()
                .vegetableDiscounts(vegetables)
                .beerDiscounts(beers)
                .breadDiscounts(breads);
    }

}
