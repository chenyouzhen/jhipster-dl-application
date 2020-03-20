package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.AlarmService;
import com.mycompany.myapp.domain.Alarm;
import com.mycompany.myapp.repository.AlarmRepository;
import com.mycompany.myapp.service.dto.AlarmDTO;
import com.mycompany.myapp.service.mapper.AlarmMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Alarm}.
 */
@Service
@Transactional
public class AlarmServiceImpl implements AlarmService {

    private final Logger log = LoggerFactory.getLogger(AlarmServiceImpl.class);

    private final AlarmRepository alarmRepository;

    private final AlarmMapper alarmMapper;

    public AlarmServiceImpl(AlarmRepository alarmRepository, AlarmMapper alarmMapper) {
        this.alarmRepository = alarmRepository;
        this.alarmMapper = alarmMapper;
    }

    /**
     * Save a alarm.
     *
     * @param alarmDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AlarmDTO save(AlarmDTO alarmDTO) {
        log.debug("Request to save Alarm : {}", alarmDTO);
        Alarm alarm = alarmMapper.toEntity(alarmDTO);
        alarm = alarmRepository.save(alarm);
        return alarmMapper.toDto(alarm);
    }

    /**
     * Get all the alarms.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<AlarmDTO> findAll() {
        log.debug("Request to get all Alarms");
        return alarmRepository.findAll().stream()
            .map(alarmMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one alarm by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AlarmDTO> findOne(Long id) {
        log.debug("Request to get Alarm : {}", id);
        return alarmRepository.findById(id)
            .map(alarmMapper::toDto);
    }

    /**
     * Delete the alarm by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Alarm : {}", id);
        alarmRepository.deleteById(id);
    }
}
