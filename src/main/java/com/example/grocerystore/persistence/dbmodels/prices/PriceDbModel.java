package com.example.grocerystore.persistence.dbmodels.prices;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "prices")
@Getter
@Setter
public class PriceDbModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @NotNull
    private double unitPrice;

    @NotNull
    private String unit;

}
