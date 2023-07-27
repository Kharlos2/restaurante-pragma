package com.example.restaurantepragma.services;

import com.example.restaurantepragma.entities.Order;
import com.example.restaurantepragma.entities.OrderMenu;
import com.example.restaurantepragma.repository.OrderMenuRepository;
import org.apache.catalina.startup.ExpandWar;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderMenuService {

    private OrderMenuRepository orderMenuRepository;

    public OrderMenu save(OrderMenu orderMenu)throws Exception{
        try {
            return orderMenuRepository.save(orderMenu);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public List<OrderMenu> findAll()throws Exception{
        try {
            return orderMenuRepository.findAll();
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

}
