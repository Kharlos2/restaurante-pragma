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
    private Integer role;
    private Integer acceptanceRole;
    private String franchise;
    private String stateRequested = "EARNING";
    @OneToMany(mappedBy = "orderId")
    @JsonManagedReference
    @JsonIgnore
    private List<OrderMenu> orderMenus;

    public Order() {
    }

    public Order(Long id, Integer role, Integer acceptanceRole, String franchise, String stateRequested, List<OrderMenu> orderMenus) {
        this.id = id;
        this.role = role;
        this.acceptanceRole = acceptanceRole;
        this.franchise = franchise;
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

    public String getFranchise() {
        return franchise;
    }

    public void setFranchise(String franchise) {
        this.franchise = franchise;
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
