package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.FactoryDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Factory}.
 */
public interface FactoryService {

    /**
     * Save a factory.
     *
     * @param factoryDTO the entity to save.
     * @return the persisted entity.
     */
    FactoryDTO save(FactoryDTO factoryDTO);

    /**
     * Get all the factories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FactoryDTO> findAll(Pageable pageable);

    /**
     * Get the "id" factory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FactoryDTO> findOne(Long id);

    /**
     * Delete the "id" factory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
