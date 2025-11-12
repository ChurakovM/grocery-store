package com.example.grocerystore.services.calculators;

import com.example.grocery.model.orders.OrderItemRequest;
import com.example.grocery.model.prices.ProductPrice;

public interface ItemPriceCalculator {
    boolean isDiscountApplied(OrderItemRequest item);
    double calculateDiscount(OrderItemRequest item, ProductPrice price);
    double calculateTotalPrice(OrderItemRequest item, ProductPrice price);
}
