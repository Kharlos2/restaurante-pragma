package com.example.restaurantepragma.enums;

public enum EmployeeResponses {

    NOT_FOUNT_EMPLOYEE("No se encontro este empleado"),
    INCORRECT_PASSWORD("Contraseña incorrecta")
    ;

    private String message;


    EmployeeResponses(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
