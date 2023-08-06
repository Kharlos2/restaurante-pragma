package com.example.restaurantepragma.dto.menu;

public class MenuUpdateDTO extends MenuDTO{
    private Integer price;
    private String description;
    private String franchise;


    public MenuUpdateDTO() {
    }

    public MenuUpdateDTO(Integer price, String description, String franchise) {
        this.price = price;
        this.description = description;
        this.franchise = franchise;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFranchise() {
        return franchise;
    }

    public void setFranchise(String franchise) {
        this.franchise = franchise;
    }

}
