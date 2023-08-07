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
    private String franchise;
    @Enumerated(EnumType.STRING)
    private OrderStatus stateRequested = OrderStatus.EARRING;
    private String orderCode;
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
    @OneToMany(mappedBy = "orderLogId")
    @JsonManagedReference
    @JsonIgnore
    private List<Logs> logs;
    public Order() {
    }

    public Order(Long id, String franchise, OrderStatus stateRequested, String orderCode, List<OrderMenu> orderMenus, Customer customerId, Employee employeeId, List<Logs> logs) {
        this.id = id;
        this.franchise = franchise;
        this.stateRequested = stateRequested;
        this.orderCode = orderCode;
        this.orderMenus = orderMenus;
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.logs = logs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public List<Logs> getLogs() {
        return logs;
    }

    public void setLogs(List<Logs> logs) {
        this.logs = logs;
    }
}
