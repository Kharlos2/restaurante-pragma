package com.example.restaurantepragma.dto.claim;

import com.example.restaurantepragma.enums.ClaimStatus;

public class ResponseClaimAdminDTO extends ClaimDTO{

    private Long id;
    private ClaimStatus estado;
    private String respuesta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClaimStatus getEstado() {
        return estado;
    }

    public void setEstado(ClaimStatus estado) {
        this.estado = estado;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
}
