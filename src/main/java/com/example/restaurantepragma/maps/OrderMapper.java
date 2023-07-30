package com.example.restaurantepragma.maps;

import com.example.restaurantepragma.dto.Order.OrderRequestDTO;
import com.example.restaurantepragma.dto.Order.ResponseOrderDTO;
import com.example.restaurantepragma.entities.Order;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mappings({
            @Mapping(source = "franchise", target = "sede"),
            @Mapping(source = "stateRequested", target = "estadoPededido"),
            @Mapping(source = "orderMenus", target = "detallesOrden")
    })
    ResponseOrderDTO toOrderDTO(Order order);
    List<ResponseOrderDTO> toOrdersDTO(List<Order> orders);

    @InheritInverseConfiguration
    @Mapping(target = "id", ignore = true)
    Order toOrder (ResponseOrderDTO responseOrderDTO);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "stateRequested", ignore = true),
            @Mapping(target = "orderMenus", ignore = true)
    })
    Order toOrder (OrderRequestDTO orderRequestDTO);

}
