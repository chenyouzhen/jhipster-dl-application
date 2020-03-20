package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.ObservationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Observation} and its DTO {@link ObservationDTO}.
 */
@Mapper(componentModel = "spring", uses = {AssemblyLineMapper.class})
public interface ObservationMapper extends EntityMapper<ObservationDTO, Observation> {

    @Mapping(source = "assemblyline.id", target = "assemblylineId")
    @Mapping(source = "assemblyline.name", target = "assemblylineName")
    ObservationDTO toDto(Observation observation);

    @Mapping(source = "assemblylineId", target = "assemblyline")
    Observation toEntity(ObservationDTO observationDTO);

    default Observation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Observation observation = new Observation();
        observation.setId(id);
        return observation;
    }
}
