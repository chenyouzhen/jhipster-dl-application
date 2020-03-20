package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Observation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Observation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ObservationRepository extends JpaRepository<Observation, Long> {
}
