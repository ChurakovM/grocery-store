package com.example.grocerystore.persistence.repos;

import com.example.grocerystore.persistence.dbmodels.prices.PriceDbModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PricesRepo extends JpaRepository<PriceDbModel, Long> {
}
