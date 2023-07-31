package com.example.restaurantepragma.enums;

public enum OrderResponses {
    NOT_FOUNT_ORDER("No se encontro esta orden.");

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
