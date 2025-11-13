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
@Table(name = "bread_discounts")
@Getter
@Setter
public class BreadDiscountDbModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private int minDays;

    private Integer maxDays;

    @NotNull
    private int buyQuantity;

    @NotNull
    private int takeQuantity;
}
