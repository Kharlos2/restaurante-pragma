package com.example.restaurantepragma.dto.Menu;

public class MenuUpdateDTO extends MenuDTO{
    private Integer price;
    private String description;
    private String campus;


    public MenuUpdateDTO() {
    }

    public MenuUpdateDTO(Integer price, String description, String campus) {
        this.price = price;
        this.description = description;
        this.campus = campus;
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

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

}
