package com.example.grocerystore.services.orders;

import com.example.grocery.model.orders.CreateOrderRequest;
import com.example.grocery.model.orders.OrderItemRequest;
import com.example.grocery.model.orders.OrderItemRequest.BeerTypeEnum;
import com.example.grocery.model.orders.ProductType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrdersBeautifierService {

    public void mergeItems(CreateOrderRequest createOrderRequest) {
        if (createOrderRequest == null ||
                createOrderRequest.getItems() == null ||
                createOrderRequest.getItems().isEmpty()) {
            return;
        }

        List<OrderItemRequest> original = createOrderRequest.getItems();
        List<OrderItemRequest> merged = new ArrayList<>();

        // 1) Vegetables — all merged together
        BigDecimal totalVegetableQuantity = BigDecimal.ZERO;

        // 2) Beers — merge by beerType
        Map<BeerTypeEnum, BigDecimal> beerTypeToQuantity = new HashMap<>();

        // 3) Breads — merge only if ageDays is the same
        Map<Integer, BigDecimal> breadAgeToQuantity = new HashMap<>();

        for (OrderItemRequest item : original) {

            ProductType type = item.getProductType();

            if (type == ProductType.VEGETABLE) {
                totalVegetableQuantity = totalVegetableQuantity.add(item.getQuantity());
                continue;
            }

            if (type == ProductType.BEER) {
                BeerTypeEnum beerType = item.getBeerType();
                beerTypeToQuantity.put(
                        beerType,
                        beerTypeToQuantity.getOrDefault(beerType, BigDecimal.ZERO).add(item.getQuantity())
                );
                continue;
            }

            if (type == ProductType.BREAD) {
                Integer age = item.getAgeDays();
                breadAgeToQuantity.put(
                        age,
                        breadAgeToQuantity.getOrDefault(age, BigDecimal.ZERO).add(item.getQuantity())
                );
            }
        }

        // Rebuild the merged list

        // VEGETABLES
        if (totalVegetableQuantity.intValue() > 0) {
            OrderItemRequest veg = new OrderItemRequest();
            veg.setProductType(ProductType.VEGETABLE);
            veg.setQuantity(totalVegetableQuantity);
            merged.add(veg);
        }

        // BEERS
        for (Map.Entry<BeerTypeEnum, BigDecimal> entry : beerTypeToQuantity.entrySet()) {
            OrderItemRequest beer = new OrderItemRequest();
            beer.setProductType(ProductType.BEER);
            beer.setBeerType(entry.getKey());
            beer.setQuantity(entry.getValue());
            merged.add(beer);
        }

        // BREADS
        for (Map.Entry<Integer, BigDecimal> entry : breadAgeToQuantity.entrySet()) {
            OrderItemRequest bread = new OrderItemRequest();
            bread.setProductType(ProductType.BREAD);
            bread.setAgeDays(entry.getKey());
            bread.setQuantity(entry.getValue());
            merged.add(bread);
        }

        // Replace original
        createOrderRequest.setItems(merged);
    }
}
