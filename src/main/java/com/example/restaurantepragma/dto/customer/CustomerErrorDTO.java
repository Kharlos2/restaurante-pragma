package com.example.restaurantepragma.dto.customer;

public class CustomerErrorDTO extends CustomerDTO{

    private String message;

    public CustomerErrorDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
