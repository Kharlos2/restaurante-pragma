package com.example.restaurantepragma.enums;

public enum MenuResponses {
    EMPTY_FIELDS("Debe diligenciar todos los campos."),
    NO_ADMIN("Solo los administradores pueden crear pedidos."),
    WRONG_PRICE("El precio tiene que ser un numero positivo y mayor a 0."),
    INCORRECT_CATEGORY("Debe ingresar una categoria valida"),
    INCORRECT_PREPARATION_TIME("Debe ingresar un tiempo de preparación correcto."),
    INCORRECT_VENUE("Debe ingresar una sede correcta"),
    PLATE_NOT_FOUND("No se encontro este plato"),
    INCORRECT_DESCRIPTION("Debe ingresar una descripción"),
    EXISTING_PLATE("Este plato ya existe."),
    INNACTIVE_PLATE("Plato innactivo.")
    ;

    private String message;

    MenuResponses(String mensaje) {
        this.message = mensaje;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
