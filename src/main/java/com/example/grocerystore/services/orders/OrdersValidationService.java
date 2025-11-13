package com.example.grocerystore.services.orders;

import com.example.grocery.model.discounts.BreadDiscount;
import com.example.grocery.model.orders.CreateOrderRequest;
import com.example.grocery.model.orders.OrderItemRequest;
import com.example.grocery.model.orders.ProductType;
import com.example.grocerystore.services.cache.DiscountsCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdersValidationService {

    private final DiscountsCacheService discountsCacheService;

    public void validateItems(CreateOrderRequest createOrderRequest) {
        if (createOrderRequest == null ||
                createOrderRequest.getItems() == null ||
                createOrderRequest.getItems().isEmpty()) {
            throw new IllegalArgumentException("There is no data in the request");
        }

        List<OrderItemRequest> requestedItems = createOrderRequest.getItems();

        for (OrderItemRequest requestedItem : requestedItems) {
            ProductType productType = requestedItem.getProductType();

            checkQuantityIsPositiveNumber(requestedItem);

            if (productType == ProductType.BREAD) {
                beerTypeMustNotBeSpecified(requestedItem);
                checkAgeDays(requestedItem);
                return;
            }

            if (productType == ProductType.BEER) {
                ageDaysMustNotBeProvided(requestedItem);
                beerTypeMustBeProvided(requestedItem);
                return;
            }

            if (productType == ProductType.VEGETABLE) {
                beerTypeMustNotBeSpecified(requestedItem);
                ageDaysMustNotBeProvided(requestedItem);
            }
        }
    }

    private void checkQuantityIsPositiveNumber(OrderItemRequest item) {
        BigDecimal quantity = item.getQuantity();
        if (quantity == null || quantity.intValue() <= 0) {
            throw new IllegalArgumentException("Quantity must be provided and be a positive number");
        }
    }

    private void checkAgeDays(OrderItemRequest item) {
        if (item.getAgeDays() == null || item.getAgeDays() < 0) {
            throw new IllegalArgumentException("Age Days must be provided and cannot be negative");
        }
        List<BreadDiscount> breads = discountsCacheService.getBreads();
        Integer requestedAgeDays = item.getAgeDays();

        int unsellableMinDays = breads.stream()
                .filter(discount -> discount.getMaxDays() == null)
                .map(BreadDiscount::getMinDays)
                .findFirst()
                .orElse(Integer.MAX_VALUE); // fallback, just in case

        if (requestedAgeDays >= unsellableMinDays) {
            throw new IllegalArgumentException(
                    String.format("Bread aged %d days cannot be sold (max allowed: %d days).",
                            requestedAgeDays, unsellableMinDays - 1));
        }
    }

    private void beerTypeMustBeProvided(OrderItemRequest item) {
        if (item.getBeerType() == null) {
            throw new IllegalArgumentException("Beer type must be provided for BEER items.");
        }

        String beerTypeValue = item.getBeerType().getValue();

        try {
            // This will throw IllegalArgumentException if the value is not part of the enum
            OrderItemRequest.BeerTypeEnum.fromValue(beerTypeValue.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(
                    String.format("Invalid beer type '%s'. Allowed values are: DUTCH, BELGIAN, GERMAN.", beerTypeValue)
            );
        }
    }

    private void ageDaysMustNotBeProvided(OrderItemRequest item) {
        if (item.getAgeDays() != null) {
            throw new IllegalArgumentException("Age Days must not be specified for BEER type");
        }
    }

    private void beerTypeMustNotBeSpecified(OrderItemRequest item) {
        if (item.getBeerType() != null) {
            throw new IllegalArgumentException("Beer Type must not be specified for BREAD type");
        }
    }
}
