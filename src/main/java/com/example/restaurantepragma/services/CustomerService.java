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

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerMapper customerMapper;

    public ResponseCustomerDTO save(Customer customer)throws Exception{
        try {
            return customerMapper.toCustomerDTO(customerRepository.save(customer));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public List<ResponseCustomerDTO> findAll()throws Exception{
        try {
            return customerMapper.toCustomersDTO(customerRepository.findAll());
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
