package com.example.restaurantepragma.services;

import com.example.restaurantepragma.dto.Menu.MenuRequestDTO;
import com.example.restaurantepragma.dto.Order.OrderRequestDTO;
import com.example.restaurantepragma.dto.Order.ResponseOrderDTO;
import com.example.restaurantepragma.dto.OrderMenu.ResponseOrderMenuDTO;
import com.example.restaurantepragma.entities.*;
import com.example.restaurantepragma.enums.CustomerResponses;
import com.example.restaurantepragma.enums.MenuResponses;
import com.example.restaurantepragma.enums.OrderResponses;
import com.example.restaurantepragma.enums.OrderStatus;
import com.example.restaurantepragma.maps.OrderMapper;
import com.example.restaurantepragma.maps.OrderMenuMapper;
import com.example.restaurantepragma.repository.*;
import com.example.restaurantepragma.validations.GeneralValidations;
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
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CustomerRepository customerRepository;

    // Método para guardar una nueva orden
    public ResponseOrderDTO save(OrderRequestDTO order) throws Exception{
        try{
            // Validación del campus/franquicia
            if (GeneralValidations.validationCampus(order.getFranchise())) throw new Exception(MenuResponses.INCORRECT_FRANCHISE.getMessage());
            //Busco por el id de cliente para asignarlo al orderEntity
            Optional<Customer> customer = customerRepository.findById(order.getCustomerId());
            if (customer.isEmpty()) throw new Exception(CustomerResponses.NOT_FOUNT.getMessage());
            // Recorremos cada elemento de la lista de menús que viene en el DTO de la orden
            for(MenuRequestDTO menuRequestDTO : order.getOrderMenus()){
                // Buscamos el menú correspondiente en la base de datos
                Optional<Menu> menuOptional = menuRepository.findById(menuRequestDTO.getMenuId());
                if (menuOptional.isPresent()) {
                     // Validamos si el menú tiene una franquicia diferente a la especificada en la orden
                    if(!order.getFranchise().equals(menuOptional.get().getFranchise())) throw new Exception("El plato "+ menuOptional.get().getNameMenu()+" no esta en esta sede");
                    // Validamos si el menú está activo (estado true)
                    else if (!menuOptional.get().getState()) throw new Exception(MenuResponses.INNACTIVE_PLATE.getMessage());
                }else throw new Exception(MenuResponses.PLATE_NOT_FOUND.getMessage());
            }
            // Guardamos la orden en la base de datos y obtenemos la entidad creada
            Order orderEntity = orderRepository.save(orderMapper.toOrder(order));
            ResponseOrderDTO responseOrderDTO = orderMapper.toOrderDTO(orderEntity);
            List<OrderMenu> orderMenus = new ArrayList<>();
            for (MenuRequestDTO menuRequestDTO:order.getOrderMenus()) {
                orderMenus.add(orderMenuRepository.save(new OrderMenu(menuRequestDTO.getQuantity(), orderEntity, menuRepository.findById(menuRequestDTO.getMenuId()).get())));
            }
            responseOrderDTO.setCliente(customer.get());
            responseOrderDTO.setDetallesOrden(orderMenuMapper.toResponseOrderMenusDTO(orderMenus));
            return responseOrderDTO;
        }catch (Exception e){
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

            // Obtiene la lista de OrderMenu asociada a la orden actual.
            List<OrderMenu> orderMenus = order.get().getOrderMenus();

            // Mapea la lista de objetos OrderMenu a una lista de objetos ResponseOrderMenuDTO
            // utilizando el mapeador "orderMenuMapper" definido previamente en el código.
            List<ResponseOrderMenuDTO> responseOrderMenuDTOS = orderMenuMapper.toResponseOrderMenusDTO(orderMenus);

            // Verifica si la orden no existe (si está vacía). Si es así, lanza una excepción con un mensaje específico.
            if (order.isEmpty()) throw new Exception(OrderResponses.NOT_FOUNT_ORDER.getMessage());

            // Establece el identificador del empleado asociado a la orden actual
            order.get().setEmployeeId(employee);

            // Establece la lista de OrderMenu asociada a la orden actual.
            order.get().setOrderMenus(orderMenus);

            // Guarda la orden actualizada en la base de datos utilizando el repositorio "orderRepository".
            // La orden es guardada o actualizada en la base de datos, y el resultado es mapeado a un objeto ResponseOrderDTO.
            ResponseOrderDTO responseOrderDTO = orderMapper.toOrderDTO(orderRepository.save(order.get()));

            // Establece la lista de detalles de orden (ResponseOrderMenuDTO) en el objeto ResponseOrderDTO.
            responseOrderDTO.setDetallesOrden(responseOrderMenuDTOS);

            // Retorna el objeto ResponseOrderDTO que contiene los detalles de la orden y la información general de la orden guardada.
            return responseOrderDTO;

        }catch (Exception e){

            // En caso de que ocurra una excepción durante el proceso, se lanza una nueva excepción con el mensaje de error original.
            throw new Exception(e.getMessage());
        }
    }

    public ResponseOrderDTO updateState(Long id)throws Exception {
        try {
            // Buscamos la orden por su id en la base de datos
            Optional<Order> order = orderRepository.findById(id);

            // Verifica si la orden no existe (si está vacía). Si es así, lanza una excepción con un mensaje específico.
            if (order.isEmpty()) throw new Exception(OrderResponses.NOT_FOUNT_ORDER.getMessage());

            // Obtiene la lista de OrderMenu asociada a la orden actual.
            List<OrderMenu> orderMenus = order.get().getOrderMenus();

            // Mapea la lista de objetos OrderMenu a una lista de objetos ResponseOrderMenuDTO
            // utilizando el mapeador "orderMenuMapper" definido previamente en el código.
            List<ResponseOrderMenuDTO> responseOrderMenuDTOS = orderMenuMapper.toResponseOrderMenusDTO(orderMenus);

            // Verifica el estado de orden y si esta igual algun estado de preparacion regresa el estado en el que continua el proceso
            if (order.get().getStateRequested().equals(OrderStatus.EARRING)) order.get().setStateRequested(OrderStatus.IN_PREPARATION);
            else if (order.get().getStateRequested().equals(OrderStatus.IN_PREPARATION)) order.get().setStateRequested(OrderStatus.READY);
            else if (order.get().getStateRequested().equals(OrderStatus.READY)) order.get().setStateRequested(OrderStatus.DELIVERED);
            else if (order.get().getStateRequested().equals(OrderStatus.DELIVERED)) throw new Exception(OrderResponses.ORDER_DELIVERED.getMessage());
            else if (order.get().getStateRequested().equals(OrderStatus.CANCELLED)) throw new Exception(OrderResponses.ORDER_CANCELLED.getMessage());

            // Guarda la orden actualizada en la base de datos utilizando el repositorio "orderRepository".
            // La orden es guardada o actualizada en la base de datos, y el resultado es mapeado a un objeto ResponseOrderDTO.
            ResponseOrderDTO responseOrderDTO = orderMapper.toOrderDTO(orderRepository.save(order.get()));

            // Establece la lista de detalles de orden (ResponseOrderMenuDTO) en el objeto ResponseOrderDTO.
            responseOrderDTO.setDetallesOrden(responseOrderMenuDTOS);

            // Retorna el objeto ResponseOrderDTO que contiene los detalles de la orden y la información general de la orden guardada.
            return responseOrderDTO;

        }catch (Exception e){

            // En caso de que ocurra una excepción durante el proceso, se lanza una nueva excepción con el mensaje de error original.
            throw new Exception(e.getMessage());
        }
    }
}
