package com.example.restaurantepragma.controllers;

import com.example.restaurantepragma.dto.customer.CustomerDTO;
import com.example.restaurantepragma.dto.order.*;
import com.example.restaurantepragma.entities.Customer;
import com.example.restaurantepragma.enums.CustomerResponses;
import com.example.restaurantepragma.enums.OrderStatus;
import com.example.restaurantepragma.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @Operation(summary = "Crear una nueva orden.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Orden creada correctamente.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Customer.class)) }),
            @ApiResponse(responseCode = "400", description = "Error crear la orden.",
                    content = @Content)
    })

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

    @PostMapping("/send-notification")
    @ResponseStatus(HttpStatus.CREATED)
    public String sendNotification(@RequestBody Customer customer) {
        orderService.sendNotificationToCustomer(customer);
        return "Notificación enviada al cliente con ID: " + customer.getNameCustomer();
    }

    @Operation(summary = "Obtener lista de todas las ordenes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Customer.class)) }),
            @ApiResponse(responseCode = "400", description = "Error al obtener la lista.",
                    content = @Content)
    })

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

    @Operation(summary = "Obtener lista de ordenes por estado y franquicia.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Customer.class)) }),
            @ApiResponse(responseCode = "400", description = "Error al obtener la lista.",
                    content = @Content)
    })

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

    @Operation(summary = "Actualizar el empleado asignado a una orden.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleado actulizado exitosamente.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Customer.class)) }),
            @ApiResponse(responseCode = "400", description = "Error al actualizar al empleado.",
                    content = @Content)
    })

    // PUT para actualizar el empleado asignado a una orden
    @PutMapping("/employee/{id}/{employee}")
    public ResponseEntity<OrderDTO> uptadeEmployee(@PathVariable Long id, @RequestParam Long assignedEmployeeId ,@RequestParam Long employeeUser, @RequestParam String password) {
        try {
            // Llama al servicio para actualizar el empleado asignado a la orden y devuelve el resultado con estado HTTP 200 OK
            return ResponseEntity.ok(orderService.updateEmployee(id,assignedEmployeeId ,employeeUser, password));
        } catch (Exception e) {
            // Si ocurre una excepción, devuelve un mensaje de error con estado HTTP 400 BAD_REQUEST
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new OrderErrorDTO(e.getMessage()));
        }
    }

    @Operation(summary = "Actualizar el estado de una orden por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orden actualizada exitosamente.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Customer.class)) }),
            @ApiResponse(responseCode = "400", description = "Error al actualizar la orden.",
                    content = @Content)
    })

    @PutMapping("/employee/state/{id}")
    public ResponseEntity<OrderDTO> uptadeState(@PathVariable Long id) {
        try {
            // Llama al servicio para actualizar el empleado asignado a la orden y devuelve el resultado con estado HTTP 200 OK
            return ResponseEntity.ok(orderService.updateState(id));
        } catch (Exception e) {
            // Si ocurre una excepción, devuelve un mensaje de error con estado HTTP 400 BAD_REQUEST
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new OrderErrorDTO(e.getMessage()));
        }
    }

    @Operation(summary = "Cancelar una orden")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orden cancelada exitosamente.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Customer.class)) }),
            @ApiResponse(responseCode = "400", description = "Error cancelar la orden.",
                    content = @Content)
    })

    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<OrderDTO> cancelOrder (@PathVariable Long orderId, @RequestBody CancelOrderRequestDTO message){
        try {
            return ResponseEntity.ok(orderService.cancelOrder(orderId,message));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new OrderErrorDTO(e.getMessage()));
        }
    }

    @Operation(summary = "Obtener ordenes finalizadas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ordenes obtenidas correctamente.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Customer.class)) }),
            @ApiResponse(responseCode = "400", description = "Error obtener la orden.",
                    content = @Content)
    })

    @GetMapping("/finishTime")
    public ResponseEntity<List<OrderDTO>> findAllEndTime(){
        try {
            return ResponseEntity.ok(new ArrayList<>(orderService.findAllEndTime()));
        }catch (Exception e){
            List<OrderDTO> orderDTOList = new ArrayList<>();
            orderDTOList.add(new OrderErrorDTO(e.getMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(orderDTOList);
        }
    }
}
