package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.ObservationService;
import com.mycompany.myapp.domain.Observation;
import com.mycompany.myapp.repository.ObservationRepository;
import com.mycompany.myapp.service.dto.ObservationDTO;
import com.mycompany.myapp.service.mapper.ObservationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Observation}.
 */
@Service
@Transactional
public class ObservationServiceImpl implements ObservationService {

    private final Logger log = LoggerFactory.getLogger(ObservationServiceImpl.class);

    private final ObservationRepository observationRepository;

    private final ObservationMapper observationMapper;

    public ObservationServiceImpl(ObservationRepository observationRepository, ObservationMapper observationMapper) {
        this.observationRepository = observationRepository;
        this.observationMapper = observationMapper;
    }

    /**
     * Save a observation.
     *
     * @param observationDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ObservationDTO save(ObservationDTO observationDTO) {
        log.debug("Request to save Observation : {}", observationDTO);
        Observation observation = observationMapper.toEntity(observationDTO);
        observation = observationRepository.save(observation);
        return observationMapper.toDto(observation);
    }

    /**
     * Get all the observations.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ObservationDTO> findAll() {
        log.debug("Request to get all Observations");
        return observationRepository.findAll().stream()
            .map(observationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one observation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ObservationDTO> findOne(Long id) {
        log.debug("Request to get Observation : {}", id);
        return observationRepository.findById(id)
            .map(observationMapper::toDto);
    }

    /**
     * Delete the observation by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Observation : {}", id);
        observationRepository.deleteById(id);
    }
}
