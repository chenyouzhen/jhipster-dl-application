package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.MaterialStockService;
import com.mycompany.myapp.domain.MaterialStock;
import com.mycompany.myapp.repository.MaterialStockRepository;
import com.mycompany.myapp.service.dto.MaterialStockDTO;
import com.mycompany.myapp.service.mapper.MaterialStockMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link MaterialStock}.
 */
@Service
@Transactional
public class MaterialStockServiceImpl implements MaterialStockService {

    private final Logger log = LoggerFactory.getLogger(MaterialStockServiceImpl.class);

    private final MaterialStockRepository materialStockRepository;

    private final MaterialStockMapper materialStockMapper;

    public MaterialStockServiceImpl(MaterialStockRepository materialStockRepository, MaterialStockMapper materialStockMapper) {
        this.materialStockRepository = materialStockRepository;
        this.materialStockMapper = materialStockMapper;
    }

    /**
     * Save a materialStock.
     *
     * @param materialStockDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public MaterialStockDTO save(MaterialStockDTO materialStockDTO) {
        log.debug("Request to save MaterialStock : {}", materialStockDTO);
        MaterialStock materialStock = materialStockMapper.toEntity(materialStockDTO);
        materialStock = materialStockRepository.save(materialStock);
        return materialStockMapper.toDto(materialStock);
    }

    /**
     * Get all the materialStocks.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<MaterialStockDTO> findAll() {
        log.debug("Request to get all MaterialStocks");
        return materialStockRepository.findAll().stream()
            .map(materialStockMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     *  Get all the materialStocks where MaterialId is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<MaterialStockDTO> findAllWhereMaterialIdIsNull() {
        log.debug("Request to get all materialStocks where MaterialId is null");
        return StreamSupport
            .stream(materialStockRepository.findAll().spliterator(), false)
            .filter(materialStock -> materialStock.getMaterialId() == null)
            .map(materialStockMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one materialStock by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MaterialStockDTO> findOne(Long id) {
        log.debug("Request to get MaterialStock : {}", id);
        return materialStockRepository.findById(id)
            .map(materialStockMapper::toDto);
    }

    /**
     * Delete the materialStock by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MaterialStock : {}", id);
        materialStockRepository.deleteById(id);
    }
}
