package com.example.restaurantepragma.services;

import com.example.restaurantepragma.dto.Order.ResponseOrderDTO;
import com.example.restaurantepragma.entities.Order;
import com.example.restaurantepragma.maps.OrderMapper;
import com.example.restaurantepragma.repository.OrderRepository;
import org.apache.catalina.startup.ExpandWar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderMapper orderMapper;

    public Order save(Order order) throws Exception{
        try{
            return orderRepository.save(order);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public List<Order> findAll()throws Exception{
        try {
            return orderRepository.findAll();
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

}
