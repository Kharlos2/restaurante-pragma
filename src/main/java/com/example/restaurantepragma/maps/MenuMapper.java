package com.example.restaurantepragma.maps;


import com.example.restaurantepragma.dto.Menu.MenuStateDTO;
import com.example.restaurantepragma.dto.Menu.ResponseMenuDTO;
import com.example.restaurantepragma.entities.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MenuMapper {
    @Mappings({
            @Mapping(source = "nameMenu", target = "nombreMenu"),
            @Mapping(source = "price", target = "precio"),
            @Mapping(source = "url", target = "foto"),
            @Mapping(source = "description", target = "descripcion"),
            @Mapping(source = "campus", target = "sede")
    })
    ResponseMenuDTO toMenuDTO(Menu menu);
    List<ResponseMenuDTO> toMenusDTO(List<Menu> menus);

    MenuStateDTO toMenuState(Menu menu);


}