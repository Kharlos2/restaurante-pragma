package com.example.restaurantepragma.controllers;

import com.example.restaurantepragma.entities.OrderMenu;
import com.example.restaurantepragma.repository.OrderMenuRepository;
import com.example.restaurantepragma.services.OrderMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("orderMenu")
public class OrderMenuController {

    @Autowired
    private OrderMenuService orderMenuService;

    @PostMapping("/")
    public ResponseEntity<OrderMenu> save(@RequestBody OrderMenu orderMenu)throws Exception{
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(orderMenuService.save(orderMenu));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    @GetMapping("/")
    public ResponseEntity<List<OrderMenu>> findAll()throws Exception{
        try {
            return ResponseEntity.ok(orderMenuService.findAll());
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

}
