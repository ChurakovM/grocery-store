package com.example.grocerystore.persistence.dbmodels.discounts;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "vegetable_discounts")
@Getter
@Setter
public class VegetableDiscountDbModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer minWeightGrams;

    private Integer maxWeightGrams;

    @NotNull
    private double discountPercent;

}
