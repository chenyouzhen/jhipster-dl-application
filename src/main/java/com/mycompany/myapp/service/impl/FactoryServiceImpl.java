package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.FactoryService;
import com.mycompany.myapp.domain.Factory;
import com.mycompany.myapp.repository.FactoryRepository;
import com.mycompany.myapp.service.dto.FactoryDTO;
import com.mycompany.myapp.service.mapper.FactoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Factory}.
 */
@Service
@Transactional
public class FactoryServiceImpl implements FactoryService {

    private final Logger log = LoggerFactory.getLogger(FactoryServiceImpl.class);

    private final FactoryRepository factoryRepository;

    private final FactoryMapper factoryMapper;

    public FactoryServiceImpl(FactoryRepository factoryRepository, FactoryMapper factoryMapper) {
        this.factoryRepository = factoryRepository;
        this.factoryMapper = factoryMapper;
    }

    /**
     * Save a factory.
     *
     * @param factoryDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public FactoryDTO save(FactoryDTO factoryDTO) {
        log.debug("Request to save Factory : {}", factoryDTO);
        Factory factory = factoryMapper.toEntity(factoryDTO);
        factory = factoryRepository.save(factory);
        return factoryMapper.toDto(factory);
    }

    /**
     * Get all the factories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FactoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Factories");
        return factoryRepository.findAll(pageable)
            .map(factoryMapper::toDto);
    }

    /**
     * Get one factory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FactoryDTO> findOne(Long id) {
        log.debug("Request to get Factory : {}", id);
        return factoryRepository.findById(id)
            .map(factoryMapper::toDto);
    }

    /**
     * Delete the factory by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Factory : {}", id);
        factoryRepository.deleteById(id);
    }
}
