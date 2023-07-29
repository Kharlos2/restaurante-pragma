package com.example.restaurantepragma.dto.OrderMenu;

import com.example.restaurantepragma.entities.Menu;


public class ResponseOrderMenuDTO {

    private Integer quantity;

    private Menu menu;

    public ResponseOrderMenuDTO() {
    }

    public ResponseOrderMenuDTO(Integer quantity, Menu menu) {
        this.quantity = quantity;
        this.menu = menu;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}