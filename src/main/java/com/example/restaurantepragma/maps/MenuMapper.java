package com.example.restaurantepragma.maps;


import com.example.restaurantepragma.dto.menu.MenuStateDTO;
import com.example.restaurantepragma.dto.menu.ResponseMenuDTO;
import com.example.restaurantepragma.entities.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

// Declaración de la interfaz "MenuMapper" que se encargará de mapear los objetos entre DTOs y entidades.
@Mapper(componentModel = "spring")
public interface MenuMapper {
    // Definición del método "toMenuDTO", que convierte un objeto "Menu" a un objeto "ResponseMenuDTO".
    // Los elementos del objeto "Menu" se mapean a los elementos del objeto "ResponseMenuDTO" según las siguientes anotaciones:

    @Mappings({
            @Mapping(source = "nameMenu", target = "nombreMenu"), // El atributo "nameMenu" se mapea al atributo "nombreMenu".
            @Mapping(source = "price", target = "precio"), // El atributo "price" se mapea al atributo "precio".
            @Mapping(source = "url", target = "foto"), // El atributo "url" se mapea al atributo "foto".
            @Mapping(source = "description", target = "descripcion"), // El atributo "description" se mapea al atributo "descripcion".
            @Mapping(source = "franchise", target = "franchise") // El atributo "franchise" se mapea al atributo "franchise".
    })
    ResponseMenuDTO toMenuDTO(Menu menu);

    // Definición del método "toMenusDTO", que convierte una lista de objetos "Menu" a una lista de objetos "ResponseMenuDTO".
    // Aquí no se requiere especificar las anotaciones de mapeo, ya que se asume que los atributos tienen el mismo nombre en ambas clases.


    List<ResponseMenuDTO> toMenusDTO(List<Menu> menus);

    // Definición del método "toMenuState", que convierte un objeto "Menu" a un objeto "MenuStateDTO".
    // No se especifican anotaciones de mapeo, por lo que se asume que los atributos tienen el mismo nombre en ambas clases.
    MenuStateDTO toMenuState(Menu menu);
}