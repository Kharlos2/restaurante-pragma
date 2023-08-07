package com.example.restaurantepragma.enums;

public enum CustomerResponses {
    NOT_FOUNT("Este cliente no fue encontrado."),
    ACTIVE_ORDER("Este cliente ya tiene una orden en curso")
    ;

    private String message;

    CustomerResponses(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
