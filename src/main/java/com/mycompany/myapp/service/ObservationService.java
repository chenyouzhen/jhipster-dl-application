package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ObservationDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Observation}.
 */
public interface ObservationService {

    /**
     * Save a observation.
     *
     * @param observationDTO the entity to save.
     * @return the persisted entity.
     */
    ObservationDTO save(ObservationDTO observationDTO);

    /**
     * Get all the observations.
     *
     * @return the list of entities.
     */
    List<ObservationDTO> findAll();

    /**
     * Get the "id" observation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ObservationDTO> findOne(Long id);

    /**
     * Delete the "id" observation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
