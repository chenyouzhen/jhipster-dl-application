package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.KpiDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Kpi} and its DTO {@link KpiDTO}.
 */
@Mapper(componentModel = "spring", uses = {FactoryMapper.class})
public interface KpiMapper extends EntityMapper<KpiDTO, Kpi> {

    @Mapping(source = "factory.id", target = "factoryId")
    @Mapping(source = "factory.name", target = "factoryName")
    KpiDTO toDto(Kpi kpi);

    @Mapping(source = "factoryId", target = "factory")
    Kpi toEntity(KpiDTO kpiDTO);

    default Kpi fromId(Long id) {
        if (id == null) {
            return null;
        }
        Kpi kpi = new Kpi();
        kpi.setId(id);
        return kpi;
    }
}
