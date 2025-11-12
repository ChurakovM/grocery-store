package com.example.grocerystore.controllers;

import com.example.grocery.api.discounts.DiscountsApi;
import com.example.grocery.model.discounts.GetAllDiscountsResponse;
import com.example.grocerystore.services.DiscountsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DiscountsController implements DiscountsApi {

    private final DiscountsService discountsService;

    @Override
    public ResponseEntity<GetAllDiscountsResponse> discountsGet() {
        GetAllDiscountsResponse response = discountsService.getAllDiscounts();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
