package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Logistics;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Logistics entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LogisticsRepository extends JpaRepository<Logistics, Long> {
}
