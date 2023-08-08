package com.example.restaurantepragma.enums;

public enum ClaimResponses {

    EMPTY_CODE("Debe ingresar un codigo para la reclamación"),
    EMPTY_REASON("Debe ingresar una razón para hacer la reclamación"),
    NOT_FOUNT_CLAIM("No se encontro esta reclamación"),
    UNANSWERED("No se escribio una respuesta a la reclamación"),
    NO_STATUS("Decición vacia"),
    ANSWERED("Esta reclamación ya fue contestada")
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
