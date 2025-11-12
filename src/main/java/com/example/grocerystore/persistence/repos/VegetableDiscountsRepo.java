package com.example.grocerystore.persistence.repos;

import com.example.grocerystore.persistence.dbmodels.discounts.VegetableDiscountDbModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VegetableDiscountsRepo extends JpaRepository<VegetableDiscountDbModel, Long> {
}
