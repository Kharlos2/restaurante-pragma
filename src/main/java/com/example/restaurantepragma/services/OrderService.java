package com.example.restaurantepragma.services;

import com.example.restaurantepragma.dto.Menu.MenuRequestDTO;
import com.example.restaurantepragma.dto.Order.OrderRequestDTO;
import com.example.restaurantepragma.dto.Order.ResponseOrderDTO;
import com.example.restaurantepragma.entities.Employee;
import com.example.restaurantepragma.entities.Menu;
import com.example.restaurantepragma.entities.Order;
import com.example.restaurantepragma.entities.OrderMenu;
import com.example.restaurantepragma.enums.MenuResponses;
import com.example.restaurantepragma.enums.OrderResponses;
import com.example.restaurantepragma.enums.OrderStatus;
import com.example.restaurantepragma.maps.OrderMapper;
import com.example.restaurantepragma.maps.OrderMenuMapper;
import com.example.restaurantepragma.repository.EmployeeRepository;
import com.example.restaurantepragma.repository.MenuRepository;
import com.example.restaurantepragma.repository.OrderMenuRepository;
import com.example.restaurantepragma.repository.OrderRepository;
import com.example.restaurantepragma.validations.GeneralValidations;
import com.example.restaurantepragma.validations.OrderValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

    // No se está inyectando EmployeeRepository. Hace falta el @Autowired

    private EmployeeRepository employeeRepository;

    // Método para guardar una nueva orden
    public ResponseOrderDTO save(OrderRequestDTO order) throws Exception{
        try{
            // Validación del campus/franquicia
            if (GeneralValidations.validationCampus(order.getFranchise())) throw new Exception(MenuResponses.INCORRECT_FRANCHISE.getMessage());

            // Lista para almacenar los detalles de la orden (OrderMenu)
            List<OrderMenu> orderMenus = new ArrayList<>();

            // Lista para almacenar los platos con franquicia incorrecta
            List<String> incorrectFranchisePlate = new ArrayList<>();

            // Creación de la entidad Order a partir del DTO recibido
            Order orderEntity = new Order();

            // Recorremos cada elemento de la lista de menús que viene en el DTO de la orden
            for(MenuRequestDTO menuRequestDTO : order.getOrderMenus()){
                // Buscamos el menú correspondiente en la base de datos
                Optional<Menu> menuOptional = menuRepository.findById(menuRequestDTO.getMenuId());

                if (menuOptional.isPresent()) {
                    // Validamos si el menú tiene una franquicia diferente a la especificada en la orden
                    if (!order.getFranchise().equals(menuOptional.get().getFranchise()))
                        if(!order.getFranchise().equals(menuOptional.get().getFranchise())) incorrectFranchisePlate.add(menuOptional.get().getNameMenu());
                            // Validamos si el menú está activo (estado true)

                        else if (menuOptional.get().getState()){
                            // Guardamos la orden en la base de datos y obtenemos la entidad creada
                            orderEntity =  orderRepository.save(orderMapper.toOrder(order));
                            // Guardamos los detalles de la orden (OrderMenu) en la base de datos y obtenemos la entidad creada
                            orderMenus.add(orderMenuRepository.save(new OrderMenu(menuRequestDTO.getQuantity(),orderEntity,menuOptional.get())));
                        }
                        // Lanzamos una excepción si el menú está inactivo
                        else throw new Exception(MenuResponses.INNACTIVE_PLATE.getMessage());
                }else throw new Exception(MenuResponses.PLATE_NOT_FOUND.getMessage());
            }
            // Lanzamos una excepción si el menú está inactivo
            if (incorrectFranchisePlate.isEmpty()){
                ResponseOrderDTO responseOrderDTO = orderMapper.toOrderDTO(orderEntity);
                responseOrderDTO.setDetallesOrden(orderMenuMapper.toResponseOrderMenusDTO(orderMenus));
                return responseOrderDTO;
            }
            // Lanzamos una excepción si hay platos con franquicia incorrecta
            throw new Exception(OrderValidations.incorrectPlate(incorrectFranchisePlate));
        }catch (Exception e){
            // Lanzamos una excepción si hay platos con franquicia incorrecta
            throw new Exception(e.getMessage());
        }
    }
    // Método para obtener todas las órdenes
    public List<ResponseOrderDTO> findAll()throws Exception{
        try {
            // Obtenemos todas las órdenes de la base de datos
            List<Order> orders = orderRepository.findAll();
            List<ResponseOrderDTO> responseOrderDTOS = new ArrayList<>();

            // Para cada orden encontrada, creamos un DTO de respuesta y agregamos los detalles de la orden (OrderMenu)
            for (Order order : orders){
                ResponseOrderDTO orderDTO = orderMapper.toOrderDTO(order);
                orderDTO.setDetallesOrden(orderMenuMapper.toResponseOrderMenusDTO(orderMenuRepository.findByOrderId(order)));
                responseOrderDTOS.add(orderDTO);
            }
            return responseOrderDTOS;
        }catch (Exception e){
            // Capturamos y relanzamos cualquier excepción ocurrida
            throw new Exception(e.getMessage());
        }
    }

    // Método para buscar órdenes por estado, franquicia y paginación
    public Page<ResponseOrderDTO> findByStateRequestedAndFranchise(OrderStatus stateRequest, String franchise, Integer role, int numberRegister, int page)throws Exception{
        try {
            // Validamos si el rol es de administrador (role = 1)
            if (role!=1)throw new Exception(MenuResponses.NO_ADMIN.getMessage());

            // Creamos una página con la información de las órdenes solicitadas
            Pageable pageable = PageRequest.of((page-1),numberRegister);
            Page<Order> paginatedOrders = orderRepository.findByStateRequestedAndFranchise(stateRequest,franchise,pageable);
            List<ResponseOrderDTO> responseOrderDTOS = new ArrayList<>();

            // Para cada orden encontrada en la página, creamos un DTO de respuesta y agregamos los detalles de la orden (OrderMenu)
            for (Order order: paginatedOrders){
                ResponseOrderDTO orderDTO = orderMapper.toOrderDTO(order);
                orderDTO.setDetallesOrden(orderMenuMapper.toResponseOrderMenusDTO(orderMenuRepository.findByOrderId(order)));
                responseOrderDTOS.add(orderDTO);
            }
            // Creamos y devolvemos una nueva página con los resultados y la paginación
            return new PageImpl<>(responseOrderDTOS,pageable,responseOrderDTOS.size());
        }catch (Exception e){
            // Capturamos y relanzamos cualquier excepción ocurrida
            throw new Exception(e.getMessage());
        }
    }
    // Método para actualizar el empleado asignado a una orden
    public ResponseOrderDTO updateEmployee(Long id,Long employeeId)throws Exception {
        try {
            // Buscamos el empleado por su id en la base de datos
            Employee employee = employeeRepository.findByEmployeeId(employeeId);
            // Buscamos la orden por su id en la base de datos
            Optional<Order> order = orderRepository.findById(id);
            if (order.isPresent()) {
                // Asignamos el empleado a la orden y guardamos los cambios en la base de datos
                order.get().setEmployeeId(employee);
                return orderMapper.toOrderDTO(orderRepository.save(order.get()));
            }
            // Lanzamos una excepción si no se encuentra la orden
            else throw new Exception(OrderResponses.NOT_FOUNT_ORDER.getMessage());
        } catch (Exception e) {
            // Capturamos y relanzamos cualquier excepción ocurrida
            throw new Exception(e.getMessage());
        }
    }
}
