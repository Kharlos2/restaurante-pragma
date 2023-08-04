package com.example.restaurantepragma.enums;

public enum CustomerResponses {
    NOT_FOUNT("Este cliente no fue encontrado.");

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
