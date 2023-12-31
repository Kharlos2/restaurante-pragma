package com.example.restaurantepragma.dto.order;

import com.example.restaurantepragma.dto.orderMenu.ResponseOrderMenuDTO;
import com.example.restaurantepragma.entities.Customer;
import com.example.restaurantepragma.enums.OrderStatus;


import java.util.List;

public class ResponseOrderDTO extends OrderDTO{
    private String sede;
    private OrderStatus estadoPededido;
    private Customer cliente;
    private List<ResponseOrderMenuDTO> detallesOrden;

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public OrderStatus getEstadoPededido() {
        return estadoPededido;
    }

    public void setEstadoPededido(OrderStatus estadoPededido) {
        this.estadoPededido = estadoPededido;
    }

    public List<ResponseOrderMenuDTO> getDetallesOrden() {
        return detallesOrden;
    }

    public void setDetallesOrden(List<ResponseOrderMenuDTO> detallesOrden) {
        this.detallesOrden = detallesOrden;
    }

    public Customer getCliente() {
        return cliente;
    }

    public void setCliente(Customer cliente) {
        this.cliente = cliente;
    }
}
