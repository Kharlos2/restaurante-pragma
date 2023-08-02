package com.example.restaurantepragma.maps;

import com.example.restaurantepragma.dto.employee.ResponseEmployeeDTO;
import com.example.restaurantepragma.entities.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

// Definimos una interfaz para el mapeo de la entidad Employee a DTO (Data Transfer Object)
@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    // Anotación @Mapping para indicar cómo se debe mapear la propiedad "orders" a "ordenes" en el DTO
    @Mapping(target = "ordenes", source = "orders")

    // Anotación @Mapping para indicar cómo se debe mapear la propiedad "nameEmployee" a "nombre" en el DTO
    @Mapping(source = "nameEmployee",target = "nombre")

    // Método que convierte un objeto de tipo Employee a un objeto ResponseEmployeeDTO
    ResponseEmployeeDTO toEmployeeDTO (Employee employee);

    // Método que convierte una lista de objetos Employee a una lista de objetos ResponseEmployeeDTO
    List<ResponseEmployeeDTO> toEmployeesDTO(List<Employee> employees);

}
