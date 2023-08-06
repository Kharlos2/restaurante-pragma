package com.example.restaurantepragma.dto.order;

public class OrderErrorDTO extends OrderDTO{

    private String mensajeError;

    public OrderErrorDTO(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }
}
