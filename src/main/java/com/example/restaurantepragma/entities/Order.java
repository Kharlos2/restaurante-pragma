package com.example.restaurantepragma.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;
    private Character rol;
    private String sede;
    private Boolean state;
    @OneToMany(mappedBy = "orderId")
    @JsonManagedReference
    @JsonIgnore
    private List<OrderMenu> orderMenus;

    public Order() {
    }

    public Order(Long id, Character rol, String sede, Boolean state, List<OrderMenu> orderMenus) {
        this.id = id;
        this.rol = rol;
        this.sede = sede;
        this.state = state;
        this.orderMenus = orderMenus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public List<OrderMenu> getOrderMenus() {
        return orderMenus;
    }

    public void setOrderMenus(List<OrderMenu> orderMenus) {
        this.orderMenus = orderMenus;
    }
}
