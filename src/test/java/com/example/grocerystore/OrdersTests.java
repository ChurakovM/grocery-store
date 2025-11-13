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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class OrdersTests {

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
    void shouldThrowWhenProductTypeIsMissing() {
        CreateOrderRequest request = new CreateOrderRequest();
        OrderItemRequest item = new OrderItemRequest();
        item.setQuantity(BigDecimal.ONE);
        request.setItems(List.of(item));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> validationService.validateItems(request));

        assertTrue(ex.getMessage().contains("Product Type must be provided"));
    }

    @Test
    void shouldPassWhenProductTypeIsValid() {
        CreateOrderRequest request = new CreateOrderRequest();
        OrderItemRequest item = new OrderItemRequest();
        item.setProductType(ProductType.BREAD);
        item.setQuantity(BigDecimal.ONE);
        item.setAgeDays(1);
        request.setItems(List.of(item));

        assertDoesNotThrow(() -> validationService.validateItems(request));
    }
}
