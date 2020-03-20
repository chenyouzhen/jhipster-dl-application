package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.AssemblyLine;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AssemblyLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssemblyLineRepository extends JpaRepository<AssemblyLine, Long> {
}
