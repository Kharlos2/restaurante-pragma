package com.example.restaurantepragma.maps;

import com.example.restaurantepragma.dto.customer.CustomerLogsDTO;
import com.example.restaurantepragma.dto.customer.ResponseCustomerDTO;
import com.example.restaurantepragma.entities.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

// Anotación @Mapper indica que esta interfaz es un mapeador de objetos
@Mapper(componentModel = "spring")
public interface CustomerMapper {

    // Anotación @Mappings indica el mapeo de campos entre las clases
    // Esta función convierte un objeto Customer a un objeto ResponseCustomerDTO
    @Mappings({
            @Mapping(source = "nameCustomer", target = "nombre"),
            @Mapping(source = "status", target = "estado"),
            @Mapping(source = "orders", target = "ordenes")
    })
    ResponseCustomerDTO toCustomerDTO(Customer customer);

    // Esta función convierte una lista de objetos Customer a una lista de objetos ResponseCustomerDTO
    List<ResponseCustomerDTO> toCustomersDTO(List<Customer> customers);

    @Mappings({
            @Mapping(source = "nameCustomer", target = "nombre"),
            @Mapping(source = "orders", target = "ordenes"),
    })
    CustomerLogsDTO toCustomerLogsDTO(Customer customer);

}
