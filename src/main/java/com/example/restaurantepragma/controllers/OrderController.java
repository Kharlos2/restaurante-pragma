package com.example.restaurantepragma.controllers;

import com.example.restaurantepragma.dto.Order.OrderDTO;
import com.example.restaurantepragma.dto.Order.OrderRequestDTO;
import com.example.restaurantepragma.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/")
    public ResponseEntity<OrderDTO> save(@RequestBody OrderRequestDTO order)throws Exception{
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(orderService.save(order));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    @GetMapping("/")
    public ResponseEntity<List<OrderDTO>> findAll()throws Exception{
        try {
            return ResponseEntity.ok(new ArrayList<>(orderService.findAll()));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
