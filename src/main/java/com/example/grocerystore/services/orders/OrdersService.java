package com.example.grocerystore.services.orders;

import com.example.grocery.model.orders.*;
import com.example.grocery.model.prices.ProductPrice;
import com.example.grocerystore.services.cache.DiscountsCacheService;
import com.example.grocerystore.services.cache.PricesCacheService;
import com.example.grocerystore.services.calculators.BeerCalculator;
import com.example.grocerystore.services.calculators.BreadCalculator;
import com.example.grocerystore.services.calculators.ItemPriceCalculator;
import com.example.grocerystore.services.calculators.VegetableCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.grocerystore.services.utils.PriceUtils.roundToTwoDecimals;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final DiscountsCacheService discountsCacheService;
    private final PricesCacheService pricesCacheService;

    private ItemPriceCalculator getCalculator(OrderItemRequest item) {
        return switch (item.getProductType()) {
            case VEGETABLE -> new VegetableCalculator(discountsCacheService.getVegetables());
            case BREAD -> new BreadCalculator(discountsCacheService.getBreads());
            case BEER -> new BeerCalculator(discountsCacheService.getBeers());
        };
    }

    public CreateOrderResponse evaluateOrder(CreateOrderRequest orderRequest) {
        List<OrderItemResponse> items = orderRequest
                .getItems()
                .stream()
                .map(item -> {
                    ProductType productType = item.getProductType();
                    ProductPrice price = pricesCacheService.getPriceByProductType(productType);
                    ItemPriceCalculator calculator = getCalculator(item);

                    boolean discountApplied = calculator.isDiscountApplied(item);
                    double discountAmount = calculator.calculateDiscount(item, price);
                    double totalPriceBeforeDiscount = calculator.calculateTotalPrice(item, price) + discountAmount;
                    double totalPrice = calculator.calculateTotalPrice(item, price);
                    int freeQuantity = calculator.calculateFreeQuantity(item);

                    return new OrderItemResponse()
                            .productType(productType)
                            .quantity(item.getQuantity().intValue())
                            .unit(price.getUnit())
                            .unitPrice(price.getUnitPrice())
                            .totalPriceBeforeDiscount(totalPriceBeforeDiscount)
                            .discountApplied(discountApplied)
                            .discountAmount(discountAmount)
                            .totalPrice(totalPrice)
                            .freeQuantity(freeQuantity);
                }).toList();

        double totalPriceBeforeDiscount = items
                .stream()
                .mapToDouble(OrderItemResponse::getTotalPriceBeforeDiscount)
                .sum();

        double totalDiscountAmount = items
                .stream()
                .mapToDouble(OrderItemResponse::getDiscountAmount)
                .sum();

        double totalPrice = items
                .stream()
                .mapToDouble(OrderItemResponse::getTotalPrice)
                .sum();

        return new CreateOrderResponse()
                .items(items)
                .totalPriceBeforeDiscount(roundToTwoDecimals(totalPriceBeforeDiscount))
                .totalDiscountAmount(roundToTwoDecimals(totalDiscountAmount))
                .totalPrice(roundToTwoDecimals(totalPrice));
    }
}
