package com.example.restaurantepragma.dto.customer;

import com.example.restaurantepragma.dto.order.OrderLogsDTO;

import java.util.List;

public class CustomerLogsDTO extends CustomerDTO{

    private String nombre;
    private List<OrderLogsDTO> ordenes;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<OrderLogsDTO> getOrdenes() {
        return ordenes;
    }

    public void setOrdenes(List<OrderLogsDTO> ordenes) {
        this.ordenes = ordenes;
    }
}
