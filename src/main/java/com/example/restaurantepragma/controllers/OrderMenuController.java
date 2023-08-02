package com.example.restaurantepragma.controllers;

import com.example.restaurantepragma.entities.OrderMenu;
import com.example.restaurantepragma.repository.OrderMenuRepository;
import com.example.restaurantepragma.services.OrderMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Anotación que indica que esta clase es un controlador Rest
@RestController
@RequestMapping("orderMenu")
public class OrderMenuController {

    // Inyección de dependencia de la clase OrderMenuService
    @Autowired
    private OrderMenuService orderMenuService;

    // Método para guardar una entidad OrderMenu en la base de datos mediante una petición HTTP POST
    @PostMapping("/")
    public ResponseEntity<OrderMenu> save(@RequestBody OrderMenu orderMenu)throws Exception{
        try {
            // Se llama al método save del OrderMenuService para guardar el objeto OrderMenu en la base de datos
            return ResponseEntity.status(HttpStatus.CREATED).body(orderMenuService.save(orderMenu));
        }catch (Exception e){
            // Si ocurre una excepción, se lanza una nueva excepción con el mensaje de error original
            throw new Exception(e.getMessage());
        }
    }
    // Método para obtener todas las entidades OrderMenu almacenadas en la base de datos mediante una petición HTTP GET
    @GetMapping("/")
    public ResponseEntity<List<OrderMenu>> findAll()throws Exception{
        try {
            // Se llama al método findAll del OrderMenuService para obtener una lista de todos los OrderMenu en la base de datos
            return ResponseEntity.ok(orderMenuService.findAll());
        }catch (Exception e){
            // Si ocurre una excepción, se lanza una nueva excepción con el mensaje de error original
            throw new Exception(e.getMessage());
        }
    }

}
