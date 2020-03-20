package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.BomEntryDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.BomEntry}.
 */
public interface BomEntryService {

    /**
     * Save a bomEntry.
     *
     * @param bomEntryDTO the entity to save.
     * @return the persisted entity.
     */
    BomEntryDTO save(BomEntryDTO bomEntryDTO);

    /**
     * Get all the bomEntries.
     *
     * @return the list of entities.
     */
    List<BomEntryDTO> findAll();

    /**
     * Get the "id" bomEntry.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BomEntryDTO> findOne(Long id);

    /**
     * Delete the "id" bomEntry.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
