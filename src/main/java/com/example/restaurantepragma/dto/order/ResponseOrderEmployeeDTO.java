package com.example.restaurantepragma.dto.order;

import com.example.restaurantepragma.dto.orderMenu.ResponseOrderMenuDTO;
import com.example.restaurantepragma.entities.Customer;
import com.example.restaurantepragma.entities.Employee;
import com.example.restaurantepragma.enums.OrderStatus;

import java.util.List;

public class ResponseOrderEmployeeDTO extends OrderDTO{

    private String sede;
    private OrderStatus estadoPededido;
    private Customer cliente;
    private Employee empleado;
    private List<ResponseOrderMenuDTO> detallesOrden;

    public ResponseOrderEmployeeDTO() {
    }

    public ResponseOrderEmployeeDTO(String sede, OrderStatus estadoPededido, Customer cliente, Employee empleado, List<ResponseOrderMenuDTO> detallesOrden) {
        this.sede = sede;
        this.estadoPededido = estadoPededido;
        this.cliente = cliente;
        this.empleado = empleado;
        this.detallesOrden = detallesOrden;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public OrderStatus getEstadoPededido() {
        return estadoPededido;
    }

    public void setEstadoPededido(OrderStatus estadoPededido) {
        this.estadoPededido = estadoPededido;
    }

    public Customer getCliente() {
        return cliente;
    }

    public void setCliente(Customer cliente) {
        this.cliente = cliente;
    }

    public Employee getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Employee empleado) {
        this.empleado = empleado;
    }

    public List<ResponseOrderMenuDTO> getDetallesOrden() {
        return detallesOrden;
    }

    public void setDetallesOrden(List<ResponseOrderMenuDTO> detallesOrden) {
        this.detallesOrden = detallesOrden;
    }
}
