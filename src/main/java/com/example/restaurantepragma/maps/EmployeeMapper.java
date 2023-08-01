package com.example.restaurantepragma.maps;

import com.example.restaurantepragma.dto.employee.ResponseEmployeeDTO;
import com.example.restaurantepragma.entities.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mapping(target = "id", source = "employeeId")
    @Mapping(target = "ordenes", source = "orders")
    @Mapping(source = "nameEmployee",target = "nombre")
    ResponseEmployeeDTO toEmployeeDTO (Employee employee);
    List<ResponseEmployeeDTO> toEmployeesDTO(List<Employee> employees);

}
