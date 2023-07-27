package com.example.restaurantepragma.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long id;
    @Column(nullable = false)
    private Character role;
    @Column(name = "name_menu", nullable = false)
    private String nameMenu;
    @Column(nullable = false)
    private Integer price;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String url;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private Boolean state;
    @Column(nullable = false)
    private String campus;
    @Column(name = "preparation_time", nullable = false)
    private Double preparationTime;
    @OneToMany(mappedBy = "menuId")
    @JsonManagedReference
    @JsonIgnore
    private List<OrderMenu> orderMenus;

    public Menu() {
    }

    public Menu(Long id, Character role, String nameMenu, Integer price, String description, String url, String category, Boolean state, String campus, Double preparationTime) {
        this.id = id;
        this.role = role;
        this.nameMenu = nameMenu;
        this.price = price;
        this.description = description;
        this.url = url;
        this.category = category;
        this.state = state;
        this.campus = campus;
        this.preparationTime = preparationTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Character getRole() {
        return role;
    }

    public void setRole(Character role) {
        this.role = role;
    }

    public String getNameMenu() {
        return nameMenu;
    }

    public void setNameMenu(String nameMenu) {
        this.nameMenu = nameMenu;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public Double getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(Double preparationTime) {
        this.preparationTime = preparationTime;
    }
}
