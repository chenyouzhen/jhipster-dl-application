package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.DemandDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Demand}.
 */
public interface DemandService {

    /**
     * Save a demand.
     *
     * @param demandDTO the entity to save.
     * @return the persisted entity.
     */
    DemandDTO save(DemandDTO demandDTO);

    /**
     * Get all the demands.
     *
     * @return the list of entities.
     */
    List<DemandDTO> findAll();

    /**
     * Get the "id" demand.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DemandDTO> findOne(Long id);

    /**
     * Delete the "id" demand.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
