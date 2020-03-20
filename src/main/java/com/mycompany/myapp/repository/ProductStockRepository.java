package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ProductStock;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProductStock entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {
}
