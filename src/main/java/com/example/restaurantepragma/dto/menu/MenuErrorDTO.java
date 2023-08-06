package com.example.restaurantepragma.dto.menu;

public class MenuErrorDTO extends MenuDTO{

    private String MensajeError;

    public MenuErrorDTO(String mensajeError) {
        MensajeError = mensajeError;
    }

    public String getMensajeError() {
        return MensajeError;
    }

    public void setMensajeError(String mensajeError) {
        MensajeError = mensajeError;
    }
}
