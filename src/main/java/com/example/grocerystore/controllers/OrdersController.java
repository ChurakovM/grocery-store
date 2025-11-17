package com.example.grocerystore.controllers;

import com.example.grocery.api.orders.OrdersApi;
import com.example.grocery.model.orders.CreateOrderRequest;
import com.example.grocery.model.orders.CreateOrderResponse;
import com.example.grocerystore.services.orders.OrdersBeautifierService;
import com.example.grocerystore.services.orders.OrdersService;
import com.example.grocerystore.services.orders.OrdersValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrdersController implements OrdersApi {

    private final OrdersService ordersService;
    private final OrdersValidationService validationService;
    private final OrdersBeautifierService beautifierService;

    @Override
    public ResponseEntity<CreateOrderResponse> ordersPost(CreateOrderRequest createOrderRequest) {
        validationService.validateItems(createOrderRequest);
        beautifierService.mergeItems(createOrderRequest);
        CreateOrderResponse response = ordersService.evaluateOrder(createOrderRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
