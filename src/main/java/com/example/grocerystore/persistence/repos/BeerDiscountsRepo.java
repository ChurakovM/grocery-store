package com.example.grocerystore.persistence.repos;

import com.example.grocerystore.persistence.dbmodels.discounts.BeerDiscountDbModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeerDiscountsRepo extends JpaRepository<BeerDiscountDbModel, Long> {
}
