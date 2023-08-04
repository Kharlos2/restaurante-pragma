package com.example.restaurantepragma.enums;

public enum OrderResponses {
    NOT_FOUNT_ORDER("No se encontro esta orden."),
    ORDER_CANCELLED("Ya se ha cancelado la orden"),
    ORDER_DELIVERED("Ya se ha entregado la orden");

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
