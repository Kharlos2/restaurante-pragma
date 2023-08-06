package com.example.restaurantepragma.dto.order;

public class CancelOrderRequestDTO extends OrderDTO{

    private String cancelMessage;

    public CancelOrderRequestDTO() {
    }

    public CancelOrderRequestDTO(String cancelMessage) {
        this.cancelMessage = cancelMessage;
    }

    public String getCancelMessage() {
        return cancelMessage;
    }

    public void setCancelMessage(String cancelMessage) {
        this.cancelMessage = cancelMessage;
    }
}
