package com.example.grocerystore.services.utils;

import com.example.grocery.model.discounts.BeerDiscount;
import com.example.grocery.model.discounts.BreadDiscount;
import com.example.grocery.model.discounts.VegetableDiscount;
import com.example.grocery.model.orders.ProductType;

import java.util.List;

import static com.example.grocery.model.orders.ProductType.VEGETABLE;
import static com.example.grocerystore.services.utils.DiscountRules.*;
import static com.example.grocerystore.services.utils.PriceUtils.roundToTwoDecimals;

public class CalculatorUtils {

    public static double calculateBeerDiscount(List<BeerDiscount> discounts,
                                               int quantity,
                                               String beerType) {
        if (beerType == null || beerType.isEmpty()) {
            return 0.0;
        }

        double totalDiscount = 0.0;

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

    public static double calculateBreadDiscount(List<BreadDiscount> discounts,
                                                int quantity,
                                                Integer ageDays,
                                                double unitPrice) {
        if (ageDays == null) {
            return 0.0;
        }

        for (BreadDiscount discount : discounts) {
            if (isDiscountForBreadsApplied(discount, ageDays)) {
                int times = quantity / discount.getTakeQuantity(); // how many full sets
                int freeItems = times * (discount.getTakeQuantity() - discount.getBuyQuantity());
                return freeItems * unitPrice;
            }
        }
        return 0.0;
    }

    public static  double calculateVegetableDiscount(List<VegetableDiscount> discounts,
                                              int quantity,
                                              double unitPrice) {
        for (VegetableDiscount discount : discounts) {
            if (isDiscountForVegetablesApplied(discount, quantity)) {
                return ((quantity / 100.0) * unitPrice) * discount.getDiscountPercent() / 100.0;
            }
        }
        return 0.0;
    }
}
