package com.example.restaurantepragma.dto.employee;

import com.example.restaurantepragma.dto.customer.CustomerDTO;

public class RankingEmployeeDTO extends EmployeeDTO {

    private Long id;
    private String nombre;
    private Long promedio;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getPromedio() {
        return promedio;
    }

    public void setPromedio(Long promedio) {
        this.promedio = promedio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
