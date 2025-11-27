package com.example.grocerystore.controllers;

import com.example.grocery.api.prices.PricesApi;
import com.example.grocery.model.prices.GetAllPricesResponse;
import com.example.grocery.model.prices.ProductPrice;
import com.example.grocery.model.prices.ProductPriceWithId;
import com.example.grocerystore.services.PricesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PricesController implements PricesApi {

    private final PricesService pricesService;

    @Override
    public ResponseEntity<GetAllPricesResponse> pricesGet() {
        GetAllPricesResponse response = pricesService.getAllPrices();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ProductPriceWithId> pricesIdPut(Long id, ProductPrice productPrice) {
        ProductPriceWithId response = pricesService.updatePrice(id, productPrice);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
}
