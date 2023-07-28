package com.example.restaurantepragma.dto.Menu;

public class MenuStateDTO extends MenuDTO{
    private String nombreMenu;
    private Integer precio;
    private Boolean state;
    private String descripcion;
    private String sede;

    public MenuStateDTO() {
    }

    public MenuStateDTO(String nombreMenu, Integer precio, Boolean state, String descripcion, String sede) {
        this.nombreMenu = nombreMenu;
        this.precio = precio;
        this.state = state;
        this.descripcion = descripcion;
        this.sede = sede;
    }

    public String getNombreMenu() {
        return nombreMenu;
    }

    public void setNombreMenu(String nombreMenu) {
        this.nombreMenu = nombreMenu;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }
}
