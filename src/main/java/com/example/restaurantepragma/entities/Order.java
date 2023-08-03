package com.example.restaurantepragma.entities;

import com.example.restaurantepragma.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @Enumerated(EnumType.STRING)
    private OrderStatus stateRequested = OrderStatus.EARRING;
    @OneToMany(mappedBy = "orderId")
    @JsonBackReference
    @JsonIgnore
    private List<OrderMenu> orderMenus;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "customer_id")
    private Customer customerId;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "employeeId")
    private Employee employeeId = null;
    public Order() {
    }

    public Order(Long id, Integer role, Integer acceptanceRole, String franchise, OrderStatus stateRequested, List<OrderMenu> orderMenus, Customer customerId, Employee employeeId) {
        this.id = id;
        this.role = role;
        this.acceptanceRole = acceptanceRole;
        this.franchise = franchise;
        this.stateRequested = stateRequested;
        this.orderMenus = orderMenus;
        this.customerId = customerId;
        this.employeeId = employeeId;
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

    public OrderStatus getStateRequested() {
        return stateRequested;
    }

    public void setStateRequested(OrderStatus stateRequested) {
        this.stateRequested = stateRequested;
    }

    public List<OrderMenu> getOrderMenus() {
        return orderMenus;
    }

    public void setOrderMenus(List<OrderMenu> orderMenus) {
        this.orderMenus = orderMenus;
    }

    public Customer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Customer customerId) {
        this.customerId = customerId;
    }

    public Employee getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Employee employeeId) {
        this.employeeId = employeeId;
    }
}
