package com.example.restaurantepragma.dto.order;

import com.example.restaurantepragma.entities.OrderMenu;

import java.util.List;

public class FinishOrderDTO extends OrderDTO{

    private Long id;
    private List<OrderMenu> detllesOrden;
    private Long tiempo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<OrderMenu> getDetllesOrden() {
        return detllesOrden;
    }

    public void setDetllesOrden(List<OrderMenu> detllesOrden) {
        this.detllesOrden = detllesOrden;
    }

    public Long getTiempo() {
        return tiempo;
    }

    public void setTiempo(Long tiempo) {
        this.tiempo = tiempo;
    }
}
