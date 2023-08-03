package com.example.restaurantepragma.controllers;

import com.example.restaurantepragma.dto.employee.EmployeeDTO;
import com.example.restaurantepragma.dto.employee.EmployeeErrorDTO;
import com.example.restaurantepragma.entities.Employee;
import com.example.restaurantepragma.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService; // Servicio utilizado para interactuar con los datos de los empleados

    @PostMapping("/")
    public ResponseEntity<EmployeeDTO> save(@RequestBody Employee employee)throws Exception{
        try {
            // Intenta guardar el empleado usando el EmployeeService y devuelve el empleado guardado en la respuesta
            return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.save(employee));
        }catch (Exception e){
            // Si ocurre una excepción durante la operación de guardado, devuelve una respuesta con el mensaje de error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new EmployeeErrorDTO(e.getMessage()));
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<EmployeeDTO>> findAll()throws Exception{
        try {
            // Intenta obtener todos los empleados usando el EmployeeService y los devuelve en la respuesta
            return ResponseEntity.ok(new ArrayList<>(employeeService.findAll()));
        }catch (Exception e){
            // Si ocurre una excepción durante la operación de obtención, devuelve una respuesta con el mensaje de error
            List<EmployeeDTO> employeeDTOS = new ArrayList<>();
            employeeDTOS.add(new EmployeeErrorDTO(e.getMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(employeeDTOS);
        }
    }

}
