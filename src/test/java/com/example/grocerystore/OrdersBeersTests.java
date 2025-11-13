package com.example.grocerystore;

import com.example.grocery.model.discounts.BreadDiscount;
import com.example.grocery.model.orders.CreateOrderRequest;
import com.example.grocery.model.orders.OrderItemRequest;
import com.example.grocery.model.orders.ProductType;
import com.example.grocerystore.services.cache.DiscountsCacheService;
import com.example.grocerystore.services.orders.OrdersValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class OrdersBeersTests {

    private OrdersValidationService validationService;

    @BeforeEach
    void setUp() {
        DiscountsCacheService discountsCacheService = Mockito.mock(DiscountsCacheService.class);
        validationService = new OrdersValidationService(discountsCacheService);

        // mock bread discounts
        List<BreadDiscount> breadDiscounts = List.of(
                new BreadDiscount() {{ setMinDays(0); setMaxDays(3); setBuyQuantity(1); setTakeQuantity(1); }},
                new BreadDiscount() {{ setMinDays(3); setMaxDays(6); setBuyQuantity(1); setTakeQuantity(2); }},
                new BreadDiscount() {{ setMinDays(6); setMaxDays(7); setBuyQuantity(1); setTakeQuantity(3); }},
                new BreadDiscount() {{ setMinDays(7); setMaxDays(null); setBuyQuantity(0); setTakeQuantity(0); }}
        );

        when(discountsCacheService.getBreads()).thenReturn(breadDiscounts);
    }

    @Test
    void validateQuantityNullOrZero_shouldThrow() {
        CreateOrderRequest request = new CreateOrderRequest();
        OrderItemRequest item = new OrderItemRequest();
        item.setProductType(ProductType.BEER);
        item.setQuantity(BigDecimal.ZERO);
        item.setBeerType(OrderItemRequest.BeerTypeEnum.DUTCH);
        request.setItems(List.of(item));

        assertThrows(IllegalArgumentException.class,
                () -> validationService.validateItems(request));

        item.setQuantity(null);
        assertThrows(IllegalArgumentException.class,
                () -> validationService.validateItems(request));
    }

    @Test
    void validateAgeDaysProvided_shouldThrow() {
        CreateOrderRequest request = new CreateOrderRequest();
        OrderItemRequest item = new OrderItemRequest();
        item.setProductType(ProductType.BEER);
        item.setQuantity(BigDecimal.ONE);
        item.setBeerType(OrderItemRequest.BeerTypeEnum.DUTCH);
        item.setAgeDays(1); // invalid for beer
        request.setItems(List.of(item));

        assertThrows(IllegalArgumentException.class,
                () -> validationService.validateItems(request));
    }

    @Test
    void validateBeerTypeNull_shouldThrow() {
        CreateOrderRequest request = new CreateOrderRequest();
        OrderItemRequest item = new OrderItemRequest();
        item.setProductType(ProductType.BEER);
        item.setQuantity(BigDecimal.ONE);
        item.setBeerType(null); // missing beer type
        request.setItems(List.of(item));

        assertThrows(IllegalArgumentException.class,
                () -> validationService.validateItems(request));
    }

    @Test
    void validateBeerTypeCaseInsensitive_shouldPass() {
        CreateOrderRequest request = new CreateOrderRequest();
        OrderItemRequest item = new OrderItemRequest();
        item.setProductType(ProductType.BEER);
        item.setQuantity(BigDecimal.ONE);

        // lowercase value → should pass
        item.setBeerType(OrderItemRequest.BeerTypeEnum.fromValue("dutch".toUpperCase()));
        request.setItems(List.of(item));

        assertDoesNotThrow(() -> validationService.validateItems(request));

        // mixed case
        item.setBeerType(OrderItemRequest.BeerTypeEnum.fromValue("BeLgIaN".toUpperCase()));
        request.setItems(List.of(item));

        assertDoesNotThrow(() -> validationService.validateItems(request));
    }

    @Test
    void validateValidBeer_shouldPass() {
        CreateOrderRequest request = new CreateOrderRequest();
        OrderItemRequest item = new OrderItemRequest();
        item.setProductType(ProductType.BEER);
        item.setQuantity(BigDecimal.valueOf(5));
        item.setBeerType(OrderItemRequest.BeerTypeEnum.DUTCH);
        request.setItems(List.of(item));

        assertDoesNotThrow(() -> validationService.validateItems(request));
    }
}
