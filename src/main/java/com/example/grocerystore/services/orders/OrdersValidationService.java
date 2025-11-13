package com.example.grocerystore.services.orders;

import com.example.grocery.model.discounts.BreadDiscount;
import com.example.grocery.model.orders.CreateOrderRequest;
import com.example.grocery.model.orders.OrderItemRequest;
import com.example.grocery.model.orders.ProductType;
import com.example.grocerystore.services.cache.DiscountsCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
            if (productType == ProductType.BREAD) {
                checkAgeDays(requestedItem);
            }
        }
    }

    private void checkAgeDays(OrderItemRequest item) {
        if (item.getAgeDays() == null) {
            return;
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
                            requestedAgeDays, unsellableMinDays - 1)
            );
        }
    }
}
