package com.example.grocerystore.services.utils;

import com.example.grocery.model.orders.ProductType;

import static com.example.grocery.model.orders.ProductType.VEGETABLE;

public class PriceUtils {

    public static double roundToTwoDecimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    public static double countTotalPriceBeforeDiscount(ProductType productType, int quantity, double unitPrice) {
        if (productType == VEGETABLE) {
            return (quantity / 100.0) * unitPrice;
        }
        return quantity * unitPrice;
    }
}
