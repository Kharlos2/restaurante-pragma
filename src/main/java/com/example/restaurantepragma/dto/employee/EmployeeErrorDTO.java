package com.example.restaurantepragma.dto.employee;

public class EmployeeErrorDTO extends EmployeeDTO{

    private String menssage;

    public EmployeeErrorDTO(String menssage) {
        this.menssage = menssage;
    }

    public String getMenssage() {
        return menssage;
    }

    public void setMenssage(String menssage) {
        this.menssage = menssage;
    }
}
