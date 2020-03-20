package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.DemandDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Demand} and its DTO {@link DemandDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductMapper.class, OrderMapper.class})
public interface DemandMapper extends EntityMapper<DemandDTO, Demand> {

    @Mapping(source = "productId.id", target = "productIdId")
    @Mapping(source = "order.id", target = "orderId")
    @Mapping(source = "order.name", target = "orderName")
    DemandDTO toDto(Demand demand);

    @Mapping(source = "productIdId", target = "productId")
    @Mapping(source = "orderId", target = "order")
    Demand toEntity(DemandDTO demandDTO);

    default Demand fromId(Long id) {
        if (id == null) {
            return null;
        }
        Demand demand = new Demand();
        demand.setId(id);
        return demand;
    }
}
