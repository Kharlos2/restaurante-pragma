package com.example.restaurantepragma.maps;

import com.example.restaurantepragma.dto.Order.OrderRequestDTO;
import com.example.restaurantepragma.dto.Order.ResponseOrderDTO;
import com.example.restaurantepragma.entities.Order;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

// La anotación @Mapper indica que esta interfaz es un mapeador que se utilizará para convertir
// entre objetos de la clase Order y los DTO (Data Transfer Objects) ResponseOrderDTO y OrderRequestDTO.

@Mapper(componentModel = "spring")
public interface OrderMapper {

    // La anotación @Mappings se utiliza para indicar las asignaciones de propiedades entre objetos.
    // En este caso, estamos definiendo cómo se deben asignar las propiedades de un objeto Order a un objeto ResponseOrderDTO.
    @Mappings({
            @Mapping(source = "franchise", target = "sede"),
            @Mapping(source = "stateRequested", target = "estadoPededido"),
            @Mapping(source = "orderMenus", target = "detallesOrden"),
            @Mapping(source = "customerId", target = "cliente")
    })
    // Este método convierte un objeto Order en un objeto ResponseOrderDTO aplicando las asignaciones definidas.
    ResponseOrderDTO toOrderDTO(Order order);

    // Este método convierte una lista de objetos Order en una lista de objetos ResponseOrderDTO.
    List<ResponseOrderDTO> toOrdersDTO(List<Order> orders);

    // La anotación @InheritInverseConfiguration indica que se deben invertir las asignaciones definidas en el método toOrderDTO.
    // Además, se ignora la propiedad 'id' del objeto Order, lo que significa que no se tendrá en cuenta al realizar la conversión
    @InheritInverseConfiguration
    @Mapping(target = "id", ignore = true)
    // Este método convierte un objeto ResponseOrderDTO en un objeto Order aplicando las asignaciones invertidas.
    Order toOrder (ResponseOrderDTO responseOrderDTO);

    // En este caso, estamos definiendo cómo se deben asignar las propiedades de un objeto OrderRequestDTO a un objeto Order.
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "stateRequested", ignore = true),
            @Mapping(target = "orderMenus", ignore = true),
            @Mapping(target = "customerId", ignore = true)
    })
    // Este método convierte un objeto OrderRequestDTO en un objeto Order aplicando las asignaciones definidas.
    Order toOrder (OrderRequestDTO orderRequestDTO);

}
