package com.example.restaurantepragma.dto.claim;

import com.example.restaurantepragma.entities.Order;
import com.example.restaurantepragma.enums.ClaimStatus;

public class ResponseClaimDTO extends ClaimDTO {

    private Long id;
    private Order orden;
    private String razon;
    private ClaimStatus estado;

    public Order getOrden() {
        return orden;
    }

    public void setOrden(Order orden) {
        this.orden = orden;
    }

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public ClaimStatus getEstado() {
        return estado;
    }

    public void setEstado(ClaimStatus estado) {
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
