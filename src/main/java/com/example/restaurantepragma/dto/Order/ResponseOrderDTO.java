package com.example.restaurantepragma.dto.Order;



public class ResponseOrderDTO extends OrderDTO{
    private Character rol;
    private String sede;
    private Boolean estado;

    public Character getRol() {
        return rol;
    }

    public void setRol(Character rol) {
        this.rol = rol;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }


}
