package com.example.restaurantepragma.services;

import com.example.restaurantepragma.dto.Menu.MenuRequestDTO;
import com.example.restaurantepragma.dto.Order.OrderRequestDTO;
import com.example.restaurantepragma.dto.Order.ResponseOrderDTO;
import com.example.restaurantepragma.entities.Menu;
import com.example.restaurantepragma.entities.Order;
import com.example.restaurantepragma.entities.OrderMenu;
import com.example.restaurantepragma.enums.MenuResponses;
import com.example.restaurantepragma.enums.OrderStatus;
import com.example.restaurantepragma.maps.OrderMapper;
import com.example.restaurantepragma.maps.OrderMenuMapper;
import com.example.restaurantepragma.repository.MenuRepository;
import com.example.restaurantepragma.repository.OrderMenuRepository;
import com.example.restaurantepragma.repository.OrderRepository;
import com.example.restaurantepragma.validations.GeneralValidations;
import com.example.restaurantepragma.validations.OrderValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
            if (GeneralValidations.validationCampus(order.getFranchise())) throw new Exception(MenuResponses.INCORRECT_FRANCHISE.getMessage());
            Order orderEntity =  orderRepository.save(orderMapper.toOrder(order));
            List<OrderMenu> orderMenus = new ArrayList<>();
            List<String> incorrectFranchisePlate = new ArrayList<>();
            for(MenuRequestDTO menuRequestDTO : order.getOrderMenus()){
                Optional<Menu> menuOptional = menuRepository.findById(menuRequestDTO.getMenuId());
                if (menuOptional.isPresent()) {
                    if(!order.getFranchise().equals(menuOptional.get().getFranchise())) incorrectFranchisePlate.add(menuOptional.get().getNameMenu());
                    else if (menuOptional.get().getState()) orderMenus.add(orderMenuRepository.save(new OrderMenu(menuRequestDTO.getQuantity(),orderEntity,menuOptional.get())));
                    else throw new Exception(MenuResponses.INNACTIVE_PLATE.getMessage());
                }else throw new Exception(MenuResponses.PLATE_NOT_FOUND.getMessage());
            }
            //Falta mirar bien porque se guarda el pedido aunque salte la excepcion
            if (incorrectFranchisePlate.isEmpty()){
                ResponseOrderDTO responseOrderDTO = orderMapper.toOrderDTO(orderEntity);
                responseOrderDTO.setDetallesOrden(orderMenuMapper.toResponseOrderMenusDTO(orderMenus));
                return responseOrderDTO;
            }else {
                System.out.println(incorrectFranchisePlate);
                throw new Exception(OrderValidations.incorrectPlate(incorrectFranchisePlate));
            }
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

    public Page<ResponseOrderDTO> findByStateRequestedAndFranchise(OrderStatus stateRequest, String franchise, Integer role, int numberRegister, int page)throws Exception{
        try {
            if (role!=1)throw new Exception(MenuResponses.NO_ADMIN.getMessage());

            Pageable pageable = PageRequest.of((page-1),numberRegister);
            Page<Order> paginatedOrders = orderRepository.findByStateRequestedAndFranchise(stateRequest,franchise,pageable);

            return paginatedOrders.map(order -> orderMapper.toOrderDTO(order));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
