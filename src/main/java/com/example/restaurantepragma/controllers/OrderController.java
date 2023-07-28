package com.example.restaurantepragma.controllers;

import com.example.restaurantepragma.entities.Order;
import com.example.restaurantepragma.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/")
    public ResponseEntity<Order> save(@RequestBody Order order)throws Exception{
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(orderService.save(order));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    @GetMapping("/")
    public ResponseEntity<List<Order>> findAll()throws Exception{
        try {
            return ResponseEntity.ok(orderService.findAll());
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
