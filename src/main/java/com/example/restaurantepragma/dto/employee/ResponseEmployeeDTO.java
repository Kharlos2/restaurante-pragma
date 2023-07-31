package com.example.restaurantepragma.dto.employee;

import com.example.restaurantepragma.entities.Order;

import java.util.List;

public class ResponseEmployeeDTO extends EmployeeDTO{

    private Long id;
    private String nombre;
    private List<Order> ordenes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Order> getOrdenes() {
        return ordenes;
    }

    public void setOrdenes(List<Order> ordenes) {
        this.ordenes = ordenes;
    }
}
