package com.example.grocerystore.services.calculators;

import com.example.grocery.model.discounts.VegetableDiscount;
import com.example.grocery.model.orders.OrderItemRequest;
import com.example.grocery.model.prices.ProductPrice;

import java.util.List;

import static com.example.grocerystore.services.utils.DiscountRules.isDiscountForVegetablesApplied;
import static com.example.grocerystore.services.utils.PriceUtils.roundToTwoDecimals;

public class VegetableCalculator implements ItemPriceCalculator {

    private final List<VegetableDiscount> discounts;

    public VegetableCalculator(List<VegetableDiscount> discounts) {
        this.discounts = discounts;
    }

    @Override
    public boolean isDiscountApplied(OrderItemRequest item) {
        int quantity = item.getQuantity().intValue();
        for (VegetableDiscount discount : discounts) {
            if (isDiscountForVegetablesApplied(discount, quantity)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public double calculateDiscount(OrderItemRequest item, ProductPrice price) {
        int quantity = item.getQuantity().intValue();
        for (VegetableDiscount discount : discounts) {
            if (isDiscountForVegetablesApplied(discount, quantity)) {
                double discountAmount = (quantity / 100.0) * price.getUnitPrice() * discount.getDiscountPercent() / 100.0;
                return roundToTwoDecimals(discountAmount);
            }
        }
        return 0.0;
    }

    @Override
    public double calculateTotalPrice(OrderItemRequest item, ProductPrice price) {
        double totalPrice = (item.getQuantity().doubleValue() / 100.0) * price.getUnitPrice();
        return roundToTwoDecimals(totalPrice - calculateDiscount(item, price));
    }

    @Override
    public int calculateFreeQuantity(OrderItemRequest item) {
        return 0;
    }
}
