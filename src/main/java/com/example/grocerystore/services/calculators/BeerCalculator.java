package com.example.grocerystore.services.calculators;

import com.example.grocery.model.discounts.BeerDiscount;
import com.example.grocery.model.orders.OrderItemRequest;
import com.example.grocery.model.prices.ProductPrice;
import com.example.grocerystore.services.cache.DiscountsCacheService;

import java.util.List;

import static com.example.grocerystore.services.utils.DiscountRules.isDiscountForBeersApplied;
import static com.example.grocerystore.services.utils.PriceUtils.roundToTwoDecimals;

public class BeerCalculator implements ItemPriceCalculator {

    private final List<BeerDiscount> discounts ;

    public BeerCalculator(List<BeerDiscount> discounts ) {
        this.discounts = discounts;
    }

    @Override
    public boolean isDiscountApplied(OrderItemRequest item) {
        int quantity = item.getQuantity().intValue();
        for (BeerDiscount discount : discounts) {
            if (isDiscountForBeersApplied(discount, quantity)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public double calculateDiscount(OrderItemRequest item, ProductPrice price) {
        int quantity = item.getQuantity().intValue();
        String beerType = item.getBeerType() != null ? item.getBeerType().getValue() : null;

        double totalDiscount = 0.0;

        if (beerType == null) {
            return totalDiscount;
        }

        for (BeerDiscount discount : discounts) {
            if (beerType.equalsIgnoreCase(discount.getBeerType()) &&
                    isDiscountForBeersApplied(discount, quantity)) {
                int packSize = discount.getPackSize();
                int fullPacks = quantity / packSize;
                totalDiscount += fullPacks * discount.getPackPrice();
                break;
            }
        }
        return roundToTwoDecimals(totalDiscount);
    }

    @Override
    public double calculateTotalPrice(OrderItemRequest item, ProductPrice price) {
        double totalPrice = item.getQuantity().doubleValue() * price.getUnitPrice();
        return roundToTwoDecimals(totalPrice - calculateDiscount(item, price));
    }
}
