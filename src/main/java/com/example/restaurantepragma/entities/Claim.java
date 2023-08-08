package com.example.restaurantepragma.entities;

import com.example.restaurantepragma.enums.ClaimStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
@Table(name = "claim")
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "claim_id")
    private Long id;
    @Enumerated(EnumType.STRING)
    private ClaimStatus claimStatus;
    private String reason;
    private String response;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "order_id")
    private Order orderId;

    public Claim() {
    }

    public Claim(Long id, Order orderId, ClaimStatus claimStatus, String reason, String response) {
        this.id = id;
        this.orderId = orderId;
        this.claimStatus = claimStatus;
        this.reason = reason;
        this.response = response;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrderId() {
        return orderId;
    }

    public void setOrderId(Order orderId) {
        this.orderId = orderId;
    }

    public ClaimStatus getClaimStatus() {
        return claimStatus;
    }

    public void setClaimStatus(ClaimStatus claimStatus) {
        this.claimStatus = claimStatus;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
