package com.example.grocerystore.services.utils;


import com.example.grocery.model.discounts.BeerDiscount;
import com.example.grocery.model.discounts.BreadDiscount;
import com.example.grocery.model.discounts.VegetableDiscount;

public class DiscountRules {

    public static boolean isDiscountForBreadsApplied(BreadDiscount discount, int ageDays) {
        if (discount == null || discount.getAgeDays() == null) {
            return false;
        }
        return ageDays == discount.getAgeDays();
    }

    public static boolean isDiscountForBeersApplied(BeerDiscount discount, int quantity) {
        if (discount == null) {
            return false;
        }
        Integer packSize = discount.getPackSize();
        return packSize != null && packSize > 0 && quantity >= packSize;
    }

    public static boolean isDiscountForVegetablesApplied(VegetableDiscount discount, int quantity) {
        if (discount == null) {
            return false;
        }
        Integer min = discount.getMinWeightGrams();
        Integer max = discount.getMaxWeightGrams();

        // Case 1: discount has both min and max range
        if (min != null && max != null) {
            return quantity >= min && quantity <= max;
        }

        // Case 2: discount only has min (open-ended upper bound)
        if (min != null) {
            return quantity >= min;
        }

        // Case 3: discount only has max (open-ended lower bound)
        if (max != null) {
            return quantity <= max;
        }

        return false;
    }
}
