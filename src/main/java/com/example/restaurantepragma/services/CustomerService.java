package com.example.restaurantepragma.services;

import com.example.restaurantepragma.dto.customer.ResponseCustomerDTO;
import com.example.restaurantepragma.entities.Customer;
import com.example.restaurantepragma.maps.CustomerMapper;
import com.example.restaurantepragma.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    // Inyección de dependencias con la anotación @Autowired
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerMapper customerMapper;

    // Método para guardar un objeto Customer en la base de datos
    public ResponseCustomerDTO save(Customer customer)throws Exception{
        try {
            // Guarda el objeto Customer en la base de datos y retorna el resultado
            // Luego, convierte el resultado en un ResponseCustomerDTO usando el CustomerMapper
            return customerMapper.toCustomerDTO(customerRepository.save(customer));
        }catch (Exception e){
            // En caso de error, lanza una excepción con el mensaje del error origina
            throw new Exception(e.getMessage());
        }
    }

    // Método para obtener todos los objetos Customer almacenados en la base de datos
    public List<ResponseCustomerDTO> findAll()throws Exception{
        try {
            // Obtiene todos los objetos Customer de la base de datos y los guarda en una lista
            // Luego, convierte la lista de Customer en una lista de ResponseCustomerDTO usando el CustomerMapper
            return customerMapper.toCustomersDTO(customerRepository.findAll());
        }catch (Exception e){
            // En caso de error, lanza una excepción con el mensaje del error original
            throw new Exception(e.getMessage());
        }
    }
}
