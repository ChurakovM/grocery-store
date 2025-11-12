package com.example.grocerystore.persistence.repos.discounts;

import com.example.grocerystore.persistence.dbmodels.discounts.BreadDiscountDbModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BreadDiscountsRepo extends JpaRepository<BreadDiscountDbModel, Long> {
}
