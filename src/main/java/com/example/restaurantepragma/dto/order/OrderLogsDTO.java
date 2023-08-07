package com.example.restaurantepragma.dto.order;

import com.example.restaurantepragma.dto.logs.ResponseLogsDTO;

import java.util.List;

public class OrderLogsDTO extends OrderDTO{

    private Long id;
    private List<ResponseLogsDTO> registros;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ResponseLogsDTO> getRegistros() {
        return registros;
    }

    public void setRegistros(List<ResponseLogsDTO> registros) {
        this.registros = registros;
    }
}
