package com.example.restaurantepragma.dto.claim;

import com.example.restaurantepragma.enums.ClaimStatus;

public class ResponseRequestClaimDTO extends ClaimDTO{

    private Long id;
    private String justificacion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

}
