package com.example.restaurantepragma.dto.Order;

import com.example.restaurantepragma.dto.OrderMenu.ResponseOrderMenuDTO;


import java.util.List;

public class ResponseOrderDTO extends OrderDTO{
    private String sede;
    private String estadoPededido;
    private List<ResponseOrderMenuDTO> detallesOrden;

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public String getEstadoPededido() {
        return estadoPededido;
    }

    public void setEstadoPededido(String estadoPededido) {
        this.estadoPededido = estadoPededido;
    }

    public List<ResponseOrderMenuDTO> getDetallesOrden() {
        return detallesOrden;
    }

    public void setDetallesOrden(List<ResponseOrderMenuDTO> detallesOrden) {
        this.detallesOrden = detallesOrden;
    }
}
