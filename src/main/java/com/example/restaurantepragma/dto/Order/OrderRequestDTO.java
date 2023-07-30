package com.example.restaurantepragma.dto.Order;

import com.example.restaurantepragma.dto.Menu.MenuRequestDTO;

import java.util.List;

public class OrderRequestDTO extends OrderDTO{
    private Integer role;
    private Integer acceptanceRole;
    private String franchise;
    private List<MenuRequestDTO> orderMenus;

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getAcceptanceRole() {
        return acceptanceRole;
    }

    public void setAcceptanceRole(Integer acceptanceRole) {
        this.acceptanceRole = acceptanceRole;
    }

    public String getFranchise() {
        return franchise;
    }

    public void setFranchise(String franchise) {
        this.franchise = franchise;
    }

    public List<MenuRequestDTO> getOrderMenus() {
        return orderMenus;
    }

    public void setOrderMenus(List<MenuRequestDTO> orderMenus) {
        this.orderMenus = orderMenus;
    }
}
