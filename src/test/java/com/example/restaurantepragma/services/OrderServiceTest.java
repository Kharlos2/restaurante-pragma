package com.example.restaurantepragma.services;


import com.example.restaurantepragma.dto.order.OrderRequestDTO;
import com.example.restaurantepragma.dto.order.ResponseOrderDTO;
import com.example.restaurantepragma.entities.*;
import com.example.restaurantepragma.enums.MenuResponses;
import com.example.restaurantepragma.enums.OrderStatus;
import com.example.restaurantepragma.maps.OrderMapper;
import com.example.restaurantepragma.maps.OrderMenuMapper;
import com.example.restaurantepragma.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private OrderMenuRepository orderMenuRepository;

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private OrderMenuMapper orderMenuMapper;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveOrder() throws Exception {
        // Mocking data and behavior
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO(); // Create a valid order request DTO
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(new Customer()));
        when(menuRepository.findById(anyLong())).thenReturn(Optional.of(new Menu()));
        when(menuRepository.findById(anyLong())).thenReturn(Optional.of(new Menu()));
        when(orderRepository.save(any())).thenReturn(new Order());
        when(orderMenuRepository.save(any())).thenReturn(new OrderMenu());

        // Test the method
        ResponseOrderDTO responseOrderDTO = orderService.save(orderRequestDTO);

        // Assertions
        assertNotNull(responseOrderDTO);
        // Add more assertions based on the expected behavior
    }

    @Test
    public void testFindAllOrders() throws Exception {
        // Mocking data and behavior
        List<Order> orders = new ArrayList<>();
        when(orderRepository.findAll()).thenReturn(orders);

        // Test the method
        List<ResponseOrderDTO> responseOrderDTOS = orderService.findAll();

        // Assertions
        assertNotNull(responseOrderDTOS);
        // Add more assertions based on the expected behavior
    }

    @Test
    public void testFindByStateRequestedAndFranchise(Order order) throws Exception {
        // Mocking data and behavior
        OrderStatus stateRequest = OrderStatus.IN_PREPARATION;
        String franchise = order.getFranchise();
        Integer role = 1;
        int numberRegister = 10;
        int page = 1;
        Pageable pageable = PageRequest.of((page - 1), numberRegister);
        List<Order> orders = new ArrayList<>();
        when(role!=1);thenThrow(new Exception(MenuResponses.NO_ADMIN.getMessage()));
        when(orderRepository.findByStateRequestedAndFranchise(any(), any(), any())).thenReturn(new PageImpl<>(orders, pageable, orders.size()));

        // Test the method
        Page<ResponseOrderDTO> responseOrdersPage = orderService.findByStateRequestedAndFranchise(stateRequest, franchise, role, numberRegister, page);

        // Assertions
        assertNotNull(responseOrdersPage);
        // Add more assertions based on the expected behavior
    }

    private void thenThrow(Exception e) {
    }


    @Test
    public void testUpdateState() throws Exception {
        // Mocking data and behavior
        Long orderId = 1L;
        Order order = new Order();
        order.setStateRequested(OrderStatus.EARRING);
        order.setOrderMenus(new ArrayList<>());
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        when(orderRepository.save(any())).thenReturn(order);
        when(orderMenuMapper.toResponseOrderMenusDTO(any())).thenReturn(new ArrayList<>());

        // Test the method
        ResponseOrderDTO responseOrderDTO = orderService.updateState(orderId);

        // Assertions
        assertNotNull(responseOrderDTO);
        // Add more assertions based on the expected behavior
    }
}