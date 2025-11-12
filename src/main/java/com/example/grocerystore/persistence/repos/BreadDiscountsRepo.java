package com.example.grocerystore.persistence.repos;

import com.example.grocerystore.persistence.dbmodels.discounts.BreadDiscountDbModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BreadDiscountsRepo extends JpaRepository<BreadDiscountDbModel, Long> {
}
