package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.MaterialStock;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MaterialStock entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MaterialStockRepository extends JpaRepository<MaterialStock, Long> {
}
