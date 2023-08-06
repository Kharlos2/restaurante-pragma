package com.example.restaurantepragma.enums;

public enum OrderResponses {
    NOT_FOUNT_ORDER("No se encontro esta orden."),
    ORDER_CANCELLED("Ya se ha cancelado la orden"),
    ORDER_DELIVERED("Ya se ha entregado la orden"),
    NO_CANCEL("Lo sentimos, tu pedido ya está en preparación y no puede cancelarse"),
    ITS_CANCELLED("Esta orden ya esta cancelada"),
    EMPTY_REASON("La cancelación debe tener un motivo")
    ;

    private String message;

    OrderResponses(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
