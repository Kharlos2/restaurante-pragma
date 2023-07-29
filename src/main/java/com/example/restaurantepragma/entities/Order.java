package com.example.restaurantepragma.entities;

import com.example.restaurantepragma.enums.OrderStatus;
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
    private Integer role;
    private Integer acceptanceRole;
    private String sede;
    private String stateRequested = "EARNING";
    @OneToMany(mappedBy = "orderId")
    @JsonManagedReference
    @JsonIgnore
    private List<OrderMenu> orderMenus;

    public Order() {
    }

    public Order(Long id, Integer role, Integer acceptanceRole, String sede, String stateRequested, List<OrderMenu> orderMenus) {
        this.id = id;
        this.role = role;
        this.acceptanceRole = acceptanceRole;
        this.sede = sede;
        this.stateRequested = stateRequested;
        this.orderMenus = orderMenus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public String getStateRequested() {
        return stateRequested;
    }

    public void setStateRequested(String stateRequested) {
        this.stateRequested = stateRequested;
    }

    public List<OrderMenu> getOrderMenus() {
        return orderMenus;
    }

    public void setOrderMenus(List<OrderMenu> orderMenus) {
        this.orderMenus = orderMenus;
    }
}
