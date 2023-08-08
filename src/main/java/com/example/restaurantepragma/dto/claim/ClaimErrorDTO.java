package com.example.restaurantepragma.dto.claim;

public class ClaimErrorDTO extends ClaimDTO{

    private String mensajeError;

    public ClaimErrorDTO(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }
}
