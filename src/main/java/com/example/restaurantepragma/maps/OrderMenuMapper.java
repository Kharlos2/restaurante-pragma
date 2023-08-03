package com.example.restaurantepragma.maps;

import com.example.restaurantepragma.dto.OrderMenu.ResponseOrderMenuDTO;
import com.example.restaurantepragma.entities.OrderMenu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

// Anotación "@Mapper" indica que esta interfaz es un mapeador de MapStruct.
// "componentModel = "spring"" indica que queremos que Spring gestione la creación y manejo de la implementación del mapeador.
@Mapper(componentModel = "spring")

public interface OrderMenuMapper {

    // Anotación "@Mapping" permite especificar cómo se debe realizar el mapeo entre las propiedades de las clases.
    @Mapping(source = "menuId",target = "menu")

    // Método que convierte un objeto "OrderMenu" en un objeto "ResponseOrderMenuDTO".
    // Este método utiliza el mapeo definido en la anotación "@Mapping".
    ResponseOrderMenuDTO toResponseOrderMenuDTO (OrderMenu orderMenu);

    // Método que convierte una lista de objetos "OrderMenu" en una lista de objetos "ResponseOrderMenuDTO".
    List<ResponseOrderMenuDTO> toResponseOrderMenusDTO (List<OrderMenu> orderMenu);

}
