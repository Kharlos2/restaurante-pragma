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


    // Inyección de dependencias del servicio CustomerService
    @Autowired
    private CustomerService customerService;

    // Manejo de solicitudes HTTP POST para guardar un cliente
    @PostMapping("/")
    public ResponseEntity<CustomerDTO> save(@RequestBody Customer customer)throws Exception{
        try {
            // Llama al servicio para guardar el cliente y devuelve la respuesta con estado HTTP 201 (CREATED)
            return ResponseEntity.status(HttpStatus.CREATED).body(customerService.save(customer));
        }catch (Exception e){

            // Si ocurre una excepción, devuelve una respuesta con estado HTTP 400 (BAD REQUEST) y un objeto CustomerErrorDTO con el mensaje de error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomerErrorDTO(e.getMessage()));
        }
    }

    // Manejo de solicitudes HTTP GET para obtener todos los clientes
    @GetMapping("/")
    public ResponseEntity<List<CustomerDTO>> findAll()throws Exception{
        try {
            // Llama al servicio para obtener la lista de clientes y devuelve la respuesta con estado HTTP 200 (OK)
            return ResponseEntity.ok(new ArrayList<>(customerService.findAll()));
        }catch (Exception e){
            // Si ocurre una excepción, crea una lista con un objeto CustomerErrorDTO que contiene el mensaje de error y devuelve una respuesta con estado HTTP 400 (BAD REQUEST)
            List<CustomerDTO> customerDTOS = new ArrayList<>();
            customerDTOS.add(new CustomerErrorDTO(e.getMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customerDTOS);
        }
    }
    @GetMapping("/logs{id}")
    public ResponseEntity<CustomerDTO> findLogsById(@PathVariable Long id){
        try {
            return ResponseEntity.ok(customerService.findLogsById(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomerErrorDTO(e.getMessage()));
        }
    }
}
