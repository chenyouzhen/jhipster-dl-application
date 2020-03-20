package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.BomEntry;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BomEntry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BomEntryRepository extends JpaRepository<BomEntry, Long> {
}
