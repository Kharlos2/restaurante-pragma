package com.example.restaurantepragma.dto.menu;

public class MenuStateDTO extends MenuDTO{
    private String nombreMenu;
    private Boolean state;
    private String sede;

    public MenuStateDTO() {
    }

    public MenuStateDTO(String nombreMenu, Boolean state, String sede) {
        this.nombreMenu = nombreMenu;
        this.state = state;
        this.sede = sede;
    }

    public String getNombreMenu() {
        return nombreMenu;
    }

    public void setNombreMenu(String nombreMenu) {
        this.nombreMenu = nombreMenu;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }
}
