package com.example.grocerystore.services;

import com.example.grocery.model.discounts.BeerDiscount;
import com.example.grocery.model.discounts.BreadDiscount;
import com.example.grocery.model.discounts.VegetableDiscount;
import com.example.grocery.model.orders.*;
import com.example.grocery.model.prices.ProductPrice;
import com.example.grocerystore.services.cache.DiscountsCacheService;
import com.example.grocerystore.services.cache.PricesCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.grocerystore.services.utils.CalculatorUtils.*;
import static com.example.grocerystore.services.utils.DiscountRules.isDiscountForBreadsApplied;
import static com.example.grocerystore.services.utils.DiscountRules.isDiscountForBeersApplied;
import static com.example.grocerystore.services.utils.DiscountRules.isDiscountForVegetablesApplied;
import static com.example.grocerystore.services.utils.PriceUtils.countTotalPriceBeforeDiscount;
import static com.example.grocerystore.services.utils.PriceUtils.roundToTwoDecimals;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final DiscountsCacheService discountsCacheService;
    private final PricesCacheService pricesCacheService;

    public CreateOrderResponse evaluateOrder(CreateOrderRequest orderRequest) {
        List<OrderItemResponse> itemResponses = orderRequest
                .getItems()
                .stream()
                .map(this::calculateItem)
                .toList();

        double totalPriceBeforeDiscount = 0.0;
        double totalDiscountAmount = 0.0;
        double totalPrice = 0.0;

        for (OrderItemResponse item : itemResponses) {
            if (item != null) {
                totalPriceBeforeDiscount += item.getTotalPriceBeforeDiscount();
                totalDiscountAmount += item.getDiscountAmount();
                totalPrice += item.getTotalPrice();
            }
        }

        totalPriceBeforeDiscount = roundToTwoDecimals(totalPriceBeforeDiscount);
        totalDiscountAmount = roundToTwoDecimals(totalDiscountAmount);
        totalPrice = roundToTwoDecimals(totalPrice);

        return new CreateOrderResponse()
                .items(itemResponses)
                .totalPriceBeforeDiscount(totalPriceBeforeDiscount)
                .totalDiscountAmount(totalDiscountAmount)
                .totalPrice(totalPrice);
    }

    private OrderItemResponse calculateItem(OrderItemRequest requestItem) {
        ProductType productType = requestItem.getProductType();
        int quantity = requestItem.getQuantity().intValue();
        Integer ageDays = requestItem.getAgeDays();
        String beerType = null;
        if (requestItem.getBeerType() != null) {
            beerType = requestItem.getBeerType().getValue();
        }

        ProductPrice productPrice = pricesCacheService.getPriceByProductType(productType);
        String unit = productPrice.getUnit();
        double unitPrice = productPrice.getUnitPrice();


        double totalPriceBeforeDiscount = countTotalPriceBeforeDiscount(productType, quantity, unitPrice);
        boolean discountApplied = isDiscountApplied(requestItem);

        double discountAmount = 0.0;
        double totalPrice = totalPriceBeforeDiscount;

        if (discountApplied) {
            switch (productType) {
                case VEGETABLE -> {
                    List<VegetableDiscount> discounts = discountsCacheService.getVegetables();
                    discountAmount = calculateVegetableDiscount(discounts, quantity, unitPrice);
                }
                case BEER -> {
                    List<BeerDiscount> discounts = discountsCacheService.getBeers();
                    discountAmount = calculateBeerDiscount(discounts, quantity, beerType);
                }
                case BREAD -> {
                    List<BreadDiscount> discounts = discountsCacheService.getBreads();
                    discountAmount = calculateBreadDiscount(discounts, quantity, ageDays, unitPrice);
                }
            }
            totalPrice = totalPriceBeforeDiscount - discountAmount;
        }

        totalPriceBeforeDiscount = roundToTwoDecimals(totalPriceBeforeDiscount);
        discountAmount = roundToTwoDecimals(discountAmount);
        totalPrice = roundToTwoDecimals(totalPrice);

        return new OrderItemResponse()
                .productType(productType)
                .quantity(quantity)
                .unit(unit)
                .unitPrice(unitPrice)
                .totalPriceBeforeDiscount(totalPriceBeforeDiscount)
                .discountApplied(discountApplied)
                .discountAmount(discountAmount)
                .totalPrice(totalPrice);
    }

    private boolean isDiscountApplied(OrderItemRequest requestItem) {
        ProductType productType = requestItem.getProductType();
        int quantity = requestItem.getQuantity().intValue();
        Integer ageDays = requestItem.getAgeDays();

        switch (productType) {
            case VEGETABLE -> {
                List<VegetableDiscount> discounts = discountsCacheService.getVegetables();
                for (VegetableDiscount discount : discounts) {
                    if (isDiscountForVegetablesApplied(discount, quantity)) {
                        return true;
                    }
                }
                return false;
            }
            case BEER -> {
                List<BeerDiscount> discounts = discountsCacheService.getBeers();
                for (BeerDiscount discount : discounts) {
                    if (isDiscountForBeersApplied(discount, quantity)) {
                        return true;
                    }
                }
                return false;
            }
            case BREAD -> {
                if (ageDays == null) {
                    return false;
                }
                List<BreadDiscount> discounts = discountsCacheService.getBreads();
                for (BreadDiscount discount : discounts) {
                    if (isDiscountForBreadsApplied(discount, ageDays)) {
                        return true;
                    }
                }
                return false;
            }
        }

        return false;
    }
}
