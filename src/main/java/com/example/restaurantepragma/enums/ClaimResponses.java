package com.example.restaurantepragma.enums;

public enum ClaimResponses {

    NOT_FOUNT_CLAIM("No se encontro esta reclamación"),
    UNANSWERED("No se escribio una respuesta a la reclamación"),
    NO_STATUS("Decición vacia")
    ;
    private String message;

    ClaimResponses(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
