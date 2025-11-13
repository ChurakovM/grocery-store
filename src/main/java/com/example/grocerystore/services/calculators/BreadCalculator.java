package com.example.grocerystore.services.calculators;

import com.example.grocery.model.discounts.BreadDiscount;
import com.example.grocery.model.orders.OrderItemRequest;
import com.example.grocery.model.prices.ProductPrice;
import com.example.grocerystore.services.cache.DiscountsCacheService;

import java.util.List;

import static com.example.grocerystore.services.utils.DiscountRules.isDiscountForBreadsApplied;
import static com.example.grocerystore.services.utils.PriceUtils.roundToTwoDecimals;

public class BreadCalculator implements ItemPriceCalculator {

    private final List<BreadDiscount> discounts;

    public BreadCalculator(List<BreadDiscount> discounts) {
        this.discounts = discounts;
    }

    @Override
    public boolean isDiscountApplied(OrderItemRequest item) {
        Integer ageDays = item.getAgeDays();
        if (ageDays == null) return false;
        for (BreadDiscount discount : discounts) {
            if (isDiscountForBreadsApplied(discount, ageDays)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public double calculateDiscount(OrderItemRequest item, ProductPrice price) {
        return 0.0;
    }

    public int calculateFreeQuantity(OrderItemRequest item) {
        Integer ageDays = item.getAgeDays();
        if (ageDays == null) return 0;

        int quantity = item.getQuantity().intValue();
        for (BreadDiscount discount : discounts) {
            if (isDiscountForBreadsApplied(discount, ageDays)) {
                int buy = discount.getBuyQuantity();
                int take= discount.getTakeQuantity();
                return (take - buy) * quantity;
            }
        }
        return 0;
    }

    @Override
    public double calculateTotalPrice(OrderItemRequest item, ProductPrice price) {
        double totalPrice = item.getQuantity().doubleValue() * price.getUnitPrice();
        return roundToTwoDecimals(totalPrice - calculateDiscount(item, price));
    }
}
