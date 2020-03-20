package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.LogisticsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Logistics} and its DTO {@link LogisticsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LogisticsMapper extends EntityMapper<LogisticsDTO, Logistics> {


    @Mapping(target = "orderId", ignore = true)
    Logistics toEntity(LogisticsDTO logisticsDTO);

    default Logistics fromId(Long id) {
        if (id == null) {
            return null;
        }
        Logistics logistics = new Logistics();
        logistics.setId(id);
        return logistics;
    }
}
