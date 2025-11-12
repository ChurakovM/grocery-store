package com.example.grocerystore.controllers;

import com.example.grocery.api.orders.OrdersApi;
import com.example.grocery.model.orders.CreateOrderRequest;
import com.example.grocery.model.orders.CreateOrderResponse;
import com.example.grocerystore.services.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrdersController implements OrdersApi {

    private final OrdersService ordersService;

    @Override
    public ResponseEntity<CreateOrderResponse> ordersPost(CreateOrderRequest createOrderRequest) {
        CreateOrderResponse response = ordersService.evaluateOrder(createOrderRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
