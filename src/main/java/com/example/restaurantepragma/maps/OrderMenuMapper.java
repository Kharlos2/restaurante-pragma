package com.example.restaurantepragma.maps;

import com.example.restaurantepragma.dto.OrderMenu.ResponseOrderMenuDTO;
import com.example.restaurantepragma.entities.OrderMenu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMenuMapper {

    @Mapping(source = "menuId",target = "menu")
    ResponseOrderMenuDTO toResponseOrderMenuDTO (OrderMenu orderMenu);
    List<ResponseOrderMenuDTO> toResponseOrderMenusDTO (List<OrderMenu> orderMenu);

}
