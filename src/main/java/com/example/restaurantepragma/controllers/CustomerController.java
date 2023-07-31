package com.example.restaurantepragma.controllers;

import com.example.restaurantepragma.dto.customer.CustomerDTO;
import com.example.restaurantepragma.dto.customer.CustomerErrorDTO;
import com.example.restaurantepragma.entities.Customer;
import com.example.restaurantepragma.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/")
    public ResponseEntity<CustomerDTO> save(@RequestBody Customer customer)throws Exception{
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(customerService.save(customer));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomerErrorDTO(e.getMessage()));
        }
    }
    @GetMapping("/")
    public ResponseEntity<List<CustomerDTO>> findAll()throws Exception{
        try {
            return ResponseEntity.ok(new ArrayList<>(customerService.findAll()));
        }catch (Exception e){
            List<CustomerDTO> customerDTOS = new ArrayList<>();
            customerDTOS.add(new CustomerErrorDTO(e.getMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customerDTOS);
        }
    }
}
