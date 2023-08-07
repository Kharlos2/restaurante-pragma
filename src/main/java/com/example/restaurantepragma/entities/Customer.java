package com.example.restaurantepragma.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id") // Definición de la columna para la clave primaria "id"
    private Long id;
    @Column(name = "name_customer") // Definición de la columna para el nombre del cliente "nameCustomer"
    private String nameCustomer;
  
    private Boolean status; // Estado del cliente, por ejemplo, activo o inactivo
    @OneToMany(mappedBy = "customerId") // Relación uno a muchos con la entidad "Order" a través del atributo "customerId" en la entidad "Order"
    @JsonBackReference
    // Anotación para evitar la recursión infinita al serializar la lista de pedidos (orden inverso de serialización)
    @JsonIgnore // Anotación para evitar la serialización de la lista de pedidos (ignora este atributo al serializar)
    private List<Order> orders; // Lista de pedidos realizados por el cliente
    public Customer() {
    }

    public Customer(Long id, String nameCustomer, Boolean status, List<Order> orders) {
        this.id = id;
        this.nameCustomer = nameCustomer;
        this.status = status;
        this.orders = orders;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

}
