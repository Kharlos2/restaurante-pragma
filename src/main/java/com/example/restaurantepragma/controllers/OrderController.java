package com.example.restaurantepragma.controllers;

import com.example.restaurantepragma.dto.Order.OrderDTO;
import com.example.restaurantepragma.dto.Order.OrderErrorDTO;
import com.example.restaurantepragma.dto.Order.OrderRequestDTO;
import com.example.restaurantepragma.dto.Order.ResponseOrderDTO;
import com.example.restaurantepragma.dto.employee.EmployeeDTO;
import com.example.restaurantepragma.enums.OrderStatus;
import com.example.restaurantepragma.services.OrderService;
import org.apache.catalina.startup.ExpandWar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new OrderErrorDTO(e.getMessage()));
        }
    }
    @GetMapping("/")
    public ResponseEntity<List<OrderDTO>> findAll()throws Exception{
        try {
            return ResponseEntity.ok(new ArrayList<>(orderService.findAll()));
        }catch (Exception e){
            List<OrderDTO> orderDTOList = new ArrayList<>();
            orderDTOList.add(new OrderErrorDTO(e.getMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(orderDTOList);
        }
    }
    @GetMapping("/state/franchise/")
    public ResponseEntity<List<OrderDTO>> findByStateRequestedAndFranchise(
            @RequestParam() OrderStatus orderStatus,
            @RequestParam() String franchise,
            @RequestParam() Integer role,
            @RequestParam() int numberRegister,
            @RequestParam() int page)throws Exception{
        try {
            Page<ResponseOrderDTO> responseOrderDTOs = orderService.findByStateRequestedAndFranchise(orderStatus,franchise,role,numberRegister,page);
            List<ResponseOrderDTO> orderList = responseOrderDTOs.getContent();
            return ResponseEntity.ok(new ArrayList<>(orderList));
        }catch (Exception e){
            List<OrderDTO> orderDTOList = new ArrayList<>();
            orderDTOList.add(new OrderErrorDTO(e.getMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(orderDTOList);
        }
    }
    @PutMapping("/employee/{id}/{employee}")
    public ResponseEntity<OrderDTO> uptadeEmployee(@PathVariable Long id, Long employee){
        try {
            return ResponseEntity.ok(orderService.updateEmployee(id,employee));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new OrderErrorDTO(e.getMessage()));
        }
    }
}
