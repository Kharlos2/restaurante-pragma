package com.example.restaurantepragma.maps;

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
            @Mapping(source = "sede", target = "sede"),
            @Mapping(source = "rol", target = "rol"),
            @Mapping(source = "state", target = "estado")
    })
    ResponseOrderDTO toOrderDTO(Order order);
    List<ResponseOrderDTO> toOrdersDTO(List<Order> orders);

    @InheritInverseConfiguration
    @Mapping(target = "id", ignore = true)
    Order toOrder (ResponseOrderDTO responseOrderDTO);
}
