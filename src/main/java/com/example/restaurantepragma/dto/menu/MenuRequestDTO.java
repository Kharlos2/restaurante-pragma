package com.example.restaurantepragma.dto.menu;

public class MenuRequestDTO extends MenuDTO{
    private Integer quantity;
    private Long menuId;

    public MenuRequestDTO(Integer quantity, Long menuId) {
        this.quantity = quantity;
        this.menuId = menuId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }
}
