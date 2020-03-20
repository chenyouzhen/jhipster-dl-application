package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.OrderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Order} and its DTO {@link OrderDTO}.
 */
@Mapper(componentModel = "spring", uses = {LogisticsMapper.class, FactoryMapper.class})
public interface OrderMapper extends EntityMapper<OrderDTO, Order> {

    @Mapping(source = "id.id", target = "idId")
    @Mapping(source = "factory.id", target = "factoryId")
    @Mapping(source = "factory.name", target = "factoryName")
    OrderDTO toDto(Order order);

    @Mapping(source = "idId", target = "id")
    @Mapping(target = "demands", ignore = true)
    @Mapping(target = "removeDemand", ignore = true)
    @Mapping(source = "factoryId", target = "factory")
    Order toEntity(OrderDTO orderDTO);

    default Order fromId(Long id) {
        if (id == null) {
            return null;
        }
        Order order = new Order();
        order.setId(id);
        return order;
    }
}
