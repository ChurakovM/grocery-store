package com.example.grocerystore.mappers;

import com.example.grocery.model.prices.ProductPrice;
import com.example.grocery.model.prices.ProductPriceWithId;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ModelMapper {

    List<ProductPrice> toListOfProductPrices(List<ProductPriceWithId> productPriceWithIds);
}
