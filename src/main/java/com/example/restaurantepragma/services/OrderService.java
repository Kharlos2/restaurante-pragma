package com.example.restaurantepragma.services;

import com.example.restaurantepragma.dto.Menu.MenuRequestDTO;
import com.example.restaurantepragma.dto.Order.OrderRequestDTO;
import com.example.restaurantepragma.dto.Order.ResponseOrderDTO;
import com.example.restaurantepragma.entities.Menu;
import com.example.restaurantepragma.entities.Order;
import com.example.restaurantepragma.entities.OrderMenu;
import com.example.restaurantepragma.enums.MenuResponses;
import com.example.restaurantepragma.maps.OrderMapper;
import com.example.restaurantepragma.maps.OrderMenuMapper;
import com.example.restaurantepragma.repository.MenuRepository;
import com.example.restaurantepragma.repository.OrderMenuRepository;
import com.example.restaurantepragma.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderMenuRepository orderMenuRepository;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private OrderMenuMapper orderMenuMapper;

    public ResponseOrderDTO save(OrderRequestDTO order) throws Exception{
        try{
            Order orderEntity =  orderRepository.save(orderMapper.toOrder(order));
            List<OrderMenu> orderMenus = new ArrayList<>();
            for(MenuRequestDTO menuRequestDTO : order.getOrderMenus()){
                Optional<Menu> menuOptional = menuRepository.findById(menuRequestDTO.getMenuId());
                if (menuOptional.isPresent()) {
                    if (menuOptional.get().getState()) orderMenus.add(orderMenuRepository.save(new OrderMenu(menuRequestDTO.getQuantity(),orderEntity,menuOptional.get())));
                    else throw new Exception(MenuResponses.INNACTIVE_PLATE.getMessage());
                }else throw new Exception(MenuResponses.PLATE_NOT_FOUND.getMessage());
            }
            ResponseOrderDTO responseOrderDTO = orderMapper.toOrderDTO(orderEntity);
            responseOrderDTO.setDetallesOrden(orderMenuMapper.toResponseOrderMenusDTO(orderMenus));
            return responseOrderDTO;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public List<ResponseOrderDTO> findAll()throws Exception{
        try {
            List<Order> orders = orderRepository.findAll();
            List<ResponseOrderDTO> responseOrderDTOS = new ArrayList<>();
            for (Order order : orders){
                ResponseOrderDTO orderDTO = orderMapper.toOrderDTO(order);
                orderDTO.setDetallesOrden(orderMenuMapper.toResponseOrderMenusDTO(orderMenuRepository.findByOrderId(order)));
                responseOrderDTOS.add(orderDTO);
            }
            return responseOrderDTOS;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

}
