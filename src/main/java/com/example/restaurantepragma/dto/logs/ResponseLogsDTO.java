package com.example.restaurantepragma.dto.logs;

import com.example.restaurantepragma.enums.OrderStatus;

import java.time.LocalDateTime;

public class ResponseLogsDTO {

    private OrderStatus estadoDeLaOrden;
    private LocalDateTime tiempoInicio;
    private LocalDateTime tiempoFin;
    private Long minutosDuracion;

    public OrderStatus getEstadoDeLaOrden() {
        return estadoDeLaOrden;
    }

    public void setEstadoDeLaOrden(OrderStatus estadoDeLaOrden) {
        this.estadoDeLaOrden = estadoDeLaOrden;
    }

    public LocalDateTime getTiempoInicio() {
        return tiempoInicio;
    }

    public void setTiempoInicio(LocalDateTime tiempoInicio) {
        this.tiempoInicio = tiempoInicio;
    }

    public Long getMinutosDuracion() {
        return minutosDuracion;
    }

    public void setMinutosDuracion(Long minutosDuracion) {
        this.minutosDuracion = minutosDuracion;
    }

    public LocalDateTime getTiempoFin() {
        return tiempoFin;
    }

    public void setTiempoFin(LocalDateTime tiempoFin) {
        this.tiempoFin = tiempoFin;
    }
}
