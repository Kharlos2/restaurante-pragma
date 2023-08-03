package com.example.restaurantepragma.services;

import com.example.restaurantepragma.entities.Order;
import com.example.restaurantepragma.entities.OrderMenu;
import com.example.restaurantepragma.repository.OrderMenuRepository;
import org.apache.catalina.startup.ExpandWar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Anotación para indicar que esta clase es un servicio y será manejada por Spring
@Service
public class OrderMenuService {

    
    // Creamos una instancia de OrderMenuRepository para acceder a los datos
    @Autowired  
    private OrderMenuRepository orderMenuRepository;

    // Método para guardar un objeto OrderMenu en la base de datos
    public OrderMenu save(OrderMenu orderMenu)throws Exception{
        try {
            // Utilizamos el OrderMenuRepository para guardar el objeto en la base de datos
            return orderMenuRepository.save(orderMenu);
        }catch (Exception e){
            // Si ocurre algún error, lanzamos una excepción con el mensaje de error
            throw new Exception(e.getMessage());
        }
    }

    // Método para obtener todos los objetos OrderMenu almacenados en la base de datos
    public List<OrderMenu> findAll()throws Exception{
        try {
            // Utilizamos el OrderMenuRepository para obtener todos los objetos de la base de datos
            return orderMenuRepository.findAll();
        }catch (Exception e){
            // Si ocurre algún error, lanzamos una excepción con el mensaje de error
            throw new Exception(e.getMessage());
        }
    }

}
