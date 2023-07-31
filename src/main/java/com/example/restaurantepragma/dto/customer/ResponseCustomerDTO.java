package com.example.restaurantepragma.dto.customer;

import com.example.restaurantepragma.entities.Order;

import java.util.List;

public class ResponseCustomerDTO extends CustomerDTO{

    private Long id;
    private String nombre;
    private Boolean estado;
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

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public List<Order> getOrdenes() {
        return ordenes;
    }

    public void setOrdenes(List<Order> ordenes) {
        this.ordenes = ordenes;
    }
}
