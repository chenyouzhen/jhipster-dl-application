package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.MaterialStockDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.MaterialStock}.
 */
public interface MaterialStockService {

    /**
     * Save a materialStock.
     *
     * @param materialStockDTO the entity to save.
     * @return the persisted entity.
     */
    MaterialStockDTO save(MaterialStockDTO materialStockDTO);

    /**
     * Get all the materialStocks.
     *
     * @return the list of entities.
     */
    List<MaterialStockDTO> findAll();
    /**
     * Get all the MaterialStockDTO where MaterialId is {@code null}.
     *
     * @return the list of entities.
     */
    List<MaterialStockDTO> findAllWhereMaterialIdIsNull();

    /**
     * Get the "id" materialStock.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MaterialStockDTO> findOne(Long id);

    /**
     * Delete the "id" materialStock.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
