package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Factory;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Factory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FactoryRepository extends JpaRepository<Factory, Long> {
}
