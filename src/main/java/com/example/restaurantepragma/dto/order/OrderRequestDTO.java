package com.example.restaurantepragma.dto.order;

import com.example.restaurantepragma.dto.menu.MenuRequestDTO;
import java.util.List;

public class OrderRequestDTO extends OrderDTO{

    private String franchise;
    private Long customerId;
    private List<MenuRequestDTO> orderMenus;

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

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
