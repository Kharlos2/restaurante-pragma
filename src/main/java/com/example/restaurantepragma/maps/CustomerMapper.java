package com.example.restaurantepragma.maps;

import com.example.restaurantepragma.dto.customer.ResponseCustomerDTO;
import com.example.restaurantepragma.entities.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    @Mappings({
            @Mapping(source = "nameCustomer", target = "nombre"),
            @Mapping(source = "status", target = "estado"),
            @Mapping(source = "orders", target = "ordenes")
    })
    ResponseCustomerDTO toCustomerDTO(Customer customer);
    List<ResponseCustomerDTO> toCustomersDTO(List<Customer> customers);


}
