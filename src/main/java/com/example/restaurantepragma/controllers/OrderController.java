package com.example.restaurantepragma.controllers;

import com.example.restaurantepragma.dto.Order.OrderDTO;
import com.example.restaurantepragma.dto.Order.OrderErrorDTO;
import com.example.restaurantepragma.dto.Order.OrderRequestDTO;
import com.example.restaurantepragma.dto.Order.ResponseOrderDTO;
import com.example.restaurantepragma.dto.employee.EmployeeDTO;
import com.example.restaurantepragma.entities.Customer;
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

    // Inyección de dependencia de OrderService

    @Autowired
    private OrderService orderService;

    // POST para crear una nueva orden
    @PostMapping("/")
    public ResponseEntity<OrderDTO> save(@RequestBody OrderRequestDTO order) throws Exception {
        try {
            // Llama al servicio para guardar la orden y devuelve el resultado con estado HTTP 201 CREATED

            return ResponseEntity.status(HttpStatus.CREATED).body(orderService.save(order));
        } catch (Exception e) {
            // Si ocurre una excepción, devuelve un mensaje de error con estado HTTP 400 BAD_REQUEST

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new OrderErrorDTO(e.getMessage()));
        }
    }

    // GET para obtener todas las órdenes
    @GetMapping("/")
    public ResponseEntity<List<OrderDTO>> findAll() throws Exception {
        try {
            // Llama al servicio para obtener todas las órdenes y devuelve la lista con estado HTTP 200 OK

            return ResponseEntity.ok(new ArrayList<>(orderService.findAll()));
        } catch (Exception e) {
            // Si ocurre una excepción, devuelve un mensaje de error con estado HTTP 400 BAD_REQUEST

            List<OrderDTO> orderDTOList = new ArrayList<>();
            orderDTOList.add(new OrderErrorDTO(e.getMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(orderDTOList);
        }
    }

    // GET para obtener órdenes por estado y franquicia
    @GetMapping("/state/franchise/")
    public ResponseEntity<List<OrderDTO>> findByStateRequestedAndFranchise(
            @RequestParam() OrderStatus orderStatus,
            @RequestParam() String franchise,
            @RequestParam() Integer role,
            @RequestParam() int numberRegister,
            @RequestParam() int page) throws Exception {
        try {
            // Llama al servicio para obtener órdenes por estado, franquicia, rol, número de registro y página
            // Devuelve una lista paginada y la convierte a una lista de OrderDTO con estado HTTP 200 OK

            Page<ResponseOrderDTO> responseOrderDTOs = orderService.findByStateRequestedAndFranchise(orderStatus, franchise, role, numberRegister, page);
            List<ResponseOrderDTO> orderList = responseOrderDTOs.getContent();
            return ResponseEntity.ok(new ArrayList<>(orderList));
        } catch (Exception e) {
            // Si ocurre una excepción, devuelve un mensaje de error con estado HTTP 400 BAD_REQUEST

            List<OrderDTO> orderDTOList = new ArrayList<>();
            orderDTOList.add(new OrderErrorDTO(e.getMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(orderDTOList);
        }
    }
    

    // PUT para actualizar el empleado asignado a una orden
    @PutMapping("/employee/{id}/{employee}")
    public ResponseEntity<OrderDTO> uptadeEmployee(@PathVariable Long id, @RequestParam Long employee) {
        try {
            // Llama al servicio para actualizar el empleado asignado a la orden y devuelve el resultado con estado HTTP 200 OK
            return ResponseEntity.ok(orderService.updateEmployee(id, employee));
        } catch (Exception e) {
            // Si ocurre una excepción, devuelve un mensaje de error con estado HTTP 400 BAD_REQUEST
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new OrderErrorDTO(e.getMessage()));
        }
    }
}
