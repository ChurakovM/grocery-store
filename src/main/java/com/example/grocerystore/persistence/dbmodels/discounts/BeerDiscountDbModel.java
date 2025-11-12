package com.example.grocerystore.persistence.dbmodels.discounts;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "beer_discounts")
@Getter
@Setter
public class BeerDiscountDbModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String beerType;

    @NotNull
    private double packPrice;

    @NotNull
    private int packSize;
}
