package com.example.restaurantepragma.entities;

import com.example.restaurantepragma.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "logs")
public class Logs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "logsId")
    private Long id;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "order_id")
    private Order orderLogId;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long time;

    public Logs() {
    }

    public Logs(Order orderLogId,OrderStatus status) {
        this.orderLogId = orderLogId;
        this.status = status;
    }

    public Logs(Long id, Order orderLogId, OrderStatus status, LocalDateTime startTime, LocalDateTime endTime, Long time) {
        this.id = id;
        this.orderLogId = orderLogId;
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrderLogId() {
        return orderLogId;
    }

    public void setOrderLogId(Order orderLogId) {
        this.orderLogId = orderLogId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
