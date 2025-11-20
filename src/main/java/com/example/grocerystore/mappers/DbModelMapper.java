package com.example.grocerystore.mappers;

import com.example.grocery.model.discounts.BeerDiscount;
import com.example.grocery.model.discounts.BreadDiscount;
import com.example.grocery.model.discounts.VegetableDiscount;
import com.example.grocery.model.prices.ProductPriceWithId;
import com.example.grocerystore.persistence.dbmodels.discounts.BeerDiscountDbModel;
import com.example.grocerystore.persistence.dbmodels.discounts.BreadDiscountDbModel;
import com.example.grocerystore.persistence.dbmodels.discounts.VegetableDiscountDbModel;
import com.example.grocerystore.persistence.dbmodels.prices.PriceDbModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface DbModelMapper {

    VegetableDiscount toVegetableDiscount(VegetableDiscountDbModel vegetableDiscountDbModel);

    BeerDiscount toBeerDiscount(BeerDiscountDbModel beerDiscountDbModel);

    BreadDiscount toBreadDiscount(BreadDiscountDbModel breadDiscountDbModel);

    ProductPriceWithId toProductPrice(PriceDbModel priceDbModel);

    PriceDbModel toPriceDbModel(ProductPriceWithId priceWithId);
}
