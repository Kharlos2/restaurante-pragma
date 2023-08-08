package com.example.restaurantepragma.services;

import com.example.restaurantepragma.dto.menu.MenuRequestDTO;
import com.example.restaurantepragma.dto.order.*;
import com.example.restaurantepragma.dto.orderMenu.ResponseOrderMenuDTO;
import com.example.restaurantepragma.entities.*;
import com.example.restaurantepragma.enums.*;
import com.example.restaurantepragma.maps.OrderMapper;
import com.example.restaurantepragma.maps.OrderMenuMapper;
import com.example.restaurantepragma.repository.*;
import com.example.restaurantepragma.utils.NotificationMananger;
import com.example.restaurantepragma.validations.GeneralValidations;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.swing.plaf.PanelUI;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

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
    @Autowired
    private LogsService logsService;
    @Autowired
    private LogsRepository logsRepository;

    // Método para guardar una nueva orden
    public ResponseOrderDTO save(OrderRequestDTO order) throws Exception {
        try {
            // Validación del campus/franquicia
            if (GeneralValidations.validationCampus(order.getFranchise()))
                throw new Exception(MenuResponses.INCORRECT_FRANCHISE.getMessage());
            //Busco por el id de cliente para asignarlo al orderEntity
            Optional<Customer> customer = customerRepository.findById(order.getCustomerId());
            if (customer.isEmpty()) throw new Exception(CustomerResponses.NOT_FOUNT.getMessage());
            else if (!customer.get().getStatus()) throw new Exception(CustomerResponses.ACTIVE_ORDER.getMessage());
            customer.get().setStatus(false);
            // Recorremos cada elemento de la lista de menús que viene en el DTO de la orden
            for (MenuRequestDTO menuRequestDTO : order.getOrderMenus()) {
                // Buscamos el menú correspondiente en la base de datos
                Optional<Menu> menuOptional = menuRepository.findById(menuRequestDTO.getMenuId());
                if (menuOptional.isPresent()) {
                    // Validamos si el menú tiene una franquicia diferente a la especificada en la orden
                    if (!order.getFranchise().equals(menuOptional.get().getFranchise()))
                        throw new Exception("El plato " + menuOptional.get().getNameMenu() + " no esta en esta sede");
                        // Validamos si el menú está activo (estado true)
                    else if (!menuOptional.get().getState())
                        throw new Exception(MenuResponses.INNACTIVE_PLATE.getMessage());
                } else throw new Exception(MenuResponses.PLATE_NOT_FOUND.getMessage());
            }
            // Guardamos la orden en la base de datos y obtenemos la entidad creada
            Order orderEntity = orderRepository.save(orderMapper.toOrder(order));
            ResponseOrderDTO responseOrderDTO = orderMapper.toOrderDTO(orderEntity);
            List<OrderMenu> orderMenus = new ArrayList<>();
            for (MenuRequestDTO menuRequestDTO : order.getOrderMenus()) {
                orderMenus.add(orderMenuRepository.save(new OrderMenu(menuRequestDTO.getQuantity(), orderEntity, menuRepository.findById(menuRequestDTO.getMenuId()).get())));
            }
            responseOrderDTO.setCliente(customer.get());
            responseOrderDTO.setDetallesOrden(orderMenuMapper.toResponseOrderMenusDTO(orderMenus));
            logsService.save(orderEntity);

            return responseOrderDTO;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    // Método para obtener todas las órdenes
    public List<ResponseOrderDTO> findAll() throws Exception {
        try {
            // Obtenemos todas las órdenes de la base de datos
            List<Order> orders = orderRepository.findAll();
            List<ResponseOrderDTO> responseOrderDTOS = new ArrayList<>();

            // Para cada orden encontrada, creamos un DTO de respuesta y agregamos los detalles de la orden (OrderMenu)
            for (Order order : orders) {
                ResponseOrderDTO orderDTO = orderMapper.toOrderDTO(order);
                orderDTO.setDetallesOrden(orderMenuMapper.toResponseOrderMenusDTO(orderMenuRepository.findByOrderId(order)));
                responseOrderDTOS.add(orderDTO);
            }
            return responseOrderDTOS;
        } catch (Exception e) {
            // Capturamos y relanzamos cualquier excepción ocurrida
            throw new Exception(e.getMessage());
        }
    }

    // Método para buscar órdenes por estado, franquicia y paginación
    public Page<ResponseOrderDTO> findByStateRequestedAndFranchise(OrderStatus stateRequest, String franchise, Integer role, int numberRegister, int page) throws Exception {
        try {
            // Validamos si el rol es de administrador (role = 1)
            if (role != 1) throw new Exception(MenuResponses.NO_ADMIN.getMessage());

            // Creamos una página con la información de las órdenes solicitadas
            Pageable pageable = PageRequest.of((page - 1), numberRegister);
            Page<Order> paginatedOrders = orderRepository.findByStateRequestedAndFranchise(stateRequest, franchise, pageable);
            List<ResponseOrderDTO> responseOrderDTOS = new ArrayList<>();

            // Para cada orden encontrada en la página, creamos un DTO de respuesta y agregamos los detalles de la orden (OrderMenu)
            for (Order order : paginatedOrders) {
                ResponseOrderDTO orderDTO = orderMapper.toOrderDTO(order);
                orderDTO.setDetallesOrden(orderMenuMapper.toResponseOrderMenusDTO(orderMenuRepository.findByOrderId(order)));
                responseOrderDTOS.add(orderDTO);
            }
            // Creamos y devolvemos una nueva página con los resultados y la paginación
            return new PageImpl<>(responseOrderDTOS, pageable, responseOrderDTOS.size());
        } catch (Exception e) {
            // Capturamos y relanzamos cualquier excepción ocurrida
            throw new Exception(e.getMessage());
        }
    }

    // Método para actualizar el empleado asignado a una orden
    public ResponseOrderEmployeeDTO updateEmployee(Long id, Long assignedEmployeeId, Long employeeUser, String password) throws Exception {
        try {
            Optional<Employee> employeeUserOptional = employeeRepository.findById(employeeUser);
            if (employeeUserOptional.isEmpty()) throw new Exception(EmployeeResponses.NOT_FOUNT_EMPLOYEE.getMessage());
            else if (!employeeUserOptional.get().getPassword().equals(password))
                throw new Exception(EmployeeResponses.INCORRECT_PASSWORD.getMessage());
            // Buscamos el empleado por su id en la base de datos
            Optional<Employee> employee = employeeRepository.findById(assignedEmployeeId);

            // Buscamos la orden por su id en la base de datos
            Optional<Order> order = orderRepository.findById(id);

            // Obtiene la lista de OrderMenu asociada a la orden actual.
            List<OrderMenu> orderMenus = order.get().getOrderMenus();

            // Mapea la lista de objetos OrderMenu a una lista de objetos ResponseOrderMenuDTO
            // utilizando el mapeador "orderMenuMapper" definido previamente en el código.
            List<ResponseOrderMenuDTO> responseOrderMenuDTOS = orderMenuMapper.toResponseOrderMenusDTO(orderMenus);

            if (employee.isEmpty()) throw new Exception(EmployeeResponses.NOT_FOUNT_EMPLOYEE.getMessage());
            // Verifica si la orden no existe (si está vacía). Si es así, lanza una excepción con un mensaje específico.
            if (order.isEmpty()) throw new Exception(OrderResponses.NOT_FOUNT_ORDER.getMessage());
            else if (order.get().getStateRequested() != OrderStatus.EARRING)
                throw new Exception("Esta orden ya tiene un empleado");

            // Establece el identificador del empleado asociado a la orden actual
            order.get().setEmployeeId(employee.get());

            // Establece la lista de OrderMenu asociada a la orden actual.
            order.get().setOrderMenus(orderMenus);
            // Asigna a la orden el estado de en preparación
            order.get().setStateRequested(OrderStatus.IN_PREPARATION);
            logsService.inPreparationSave(order.get());

            // Guarda la orden actualizada en la base de datos utilizando el repositorio "orderRepository".
            // La orden es guardada o actualizada en la base de datos, y el resultado es mapeado a un objeto ResponseOrderDTO.
            ResponseOrderEmployeeDTO responseOrderDTO = orderMapper.toOrderEmployeeDTO(orderRepository.save(order.get()));

            // Establece la lista de detalles de orden (ResponseOrderMenuDTO) en el objeto ResponseOrderDTO.
            responseOrderDTO.setDetallesOrden(responseOrderMenuDTOS);

            // Retorna el objeto ResponseOrderDTO que contiene los detalles de la orden y la información general de la orden guardada.
            return responseOrderDTO;

        } catch (Exception e) {

            // En caso de que ocurra una excepción durante el proceso, se lanza una nueva excepción con el mensaje de error original.
            throw new Exception(e.getMessage());
        }
    }

    public ResponseOrderDTO updateState(Long id) throws Exception {
        try {
            // Buscamos la orden por su id en la base de datos
            Optional<Order> orderOptional = orderRepository.findById(id);
            // Verifica si la orden no existe (si está vacía). Si es así, lanza una excepción con un mensaje específico.
            if (orderOptional.isEmpty()) throw new Exception(OrderResponses.NOT_FOUNT_ORDER.getMessage());
            Order order = orderOptional.get();
            Customer customer = order.getCustomerId();

            // Obtiene la lista de OrderMenu asociada a la orden actual.
            List<OrderMenu> orderMenus = order.getOrderMenus();

            // Mapea la lista de objetos OrderMenu a una lista de objetos ResponseOrderMenuDTO
            // utilizando el mapeador "orderMenuMapper" definido previamente en el código.
            List<ResponseOrderMenuDTO> responseOrderMenuDTOS = orderMenuMapper.toResponseOrderMenusDTO(orderMenus);

            // Verifica el estado de orden y si esta igual algun estado de preparacion regresa el estado en el que continua el proceso
            if (order.getStateRequested().equals(OrderStatus.IN_PREPARATION)) {
                order.setStateRequested(OrderStatus.READY);
                String orderCode = sendNotificationToCustomer(customer);
                order.setOrderCode(orderCode);
                logsService.readySave(order);
            } else if (order.getStateRequested().equals(OrderStatus.READY)) {
                order.setStateRequested(OrderStatus.DELIVERED);
                logsService.deliveredSave(order);
                customer.setStatus(true);
            } else if (order.getStateRequested().equals(OrderStatus.DELIVERED))
                throw new Exception(OrderResponses.ORDER_DELIVERED.getMessage());
            else if (order.getStateRequested().equals(OrderStatus.CANCELLED))
                throw new Exception(OrderResponses.ORDER_CANCELLED.getMessage());
            // Guarda la orden actualizada en la base de datos utilizando el repositorio "orderRepository".
            // La orden es guardada o actualizada en la base de datos, y el resultado es mapeado a un objeto ResponseOrderDTO.
            ResponseOrderDTO responseOrderDTO = orderMapper.toOrderDTO(orderRepository.save(order));

            // Establece la lista de detalles de orden (ResponseOrderMenuDTO) en el objeto ResponseOrderDTO.
            responseOrderDTO.setDetallesOrden(responseOrderMenuDTOS);

            // Retorna el objeto ResponseOrderDTO que contiene los detalles de la orden y la información general de la orden guardada.
            return responseOrderDTO;

        } catch (Exception e) {

            // En caso de que ocurra una excepción durante el proceso, se lanza una nueva excepción con el mensaje de error original.
            throw new Exception(e.getMessage());
        }
    }

    public String generateRandomSms() {
        String alphabet = "abcdefghijklmnopqrstvwxyz";
        int length = 5;

        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }

        return stringBuilder.toString();
    }

    public String sendNotificationToCustomer(Customer Customer) {
        // Generar un codigo aleatorio
        String randomString = generateRandomSms();
        // Crear el mensaje de notificación con el UUID aleatorio
        String message = "¡Su pedido está listo para ser reclamado! Use el siguiente código: " + randomString;

        // Llamar al método de NotificationManager para enviar la notificación
        NotificationMananger.sendNotificationToCustomer(Customer.getNameCustomer(), message);
        return randomString;
    }

    public ResponseOrderDTO cancelOrder(Long orderId, CancelOrderRequestDTO cancelOrderRequestDTO) throws Exception {
        try {
            // Intenta obtener la orden correspondiente al orderId proporcionado
            Optional<Order> orderOptional = orderRepository.findById(orderId);

            // Si no se encuentra la orden, lanza una excepción con un mensaje correspondiente
            if (orderOptional.isEmpty()) {
                throw new Exception(OrderResponses.NOT_FOUNT_ORDER.getMessage());
            }

            // Obtiene la instancia de la orden a partir del Optional
            Order order = orderOptional.get();

            // Verifica si la orden ya ha sido cancelada previamente
            if (order.getStateRequested() == OrderStatus.CANCELLED) {
                throw new Exception(OrderResponses.ITS_CANCELLED.getMessage());
            }
            // Verifica si la orden no está en estado "Earring" (pendiente)
            else if (order.getStateRequested() != OrderStatus.EARRING) {
                throw new Exception(OrderResponses.NO_CANCEL.getMessage());
            }
            // Verifica si el mensaje de cancelación está vacío
            else if (cancelOrderRequestDTO.getCancelMessage().isEmpty()) {
                throw new Exception(OrderResponses.EMPTY_REASON.getMessage());
            }

            // Obtiene la lista de elementos de menú asociados a la orden
            List<OrderMenu> orderMenus = order.getOrderMenus();

            // Mapea los elementos de menú de la orden a DTOs de respuesta
            List<ResponseOrderMenuDTO> responseOrderMenuDTOS = orderMenuMapper.toResponseOrderMenusDTO(orderMenus);

            // Cambia el estado de la orden a "CANCELLED"
            order.setStateRequested(OrderStatus.CANCELLED);

            // Guarda la orden actualizada en el repositorio y obtiene el DTO de respuesta correspondiente
            ResponseOrderDTO responseOrderDTO = orderMapper.toOrderDTO(orderRepository.save(order));

            // Establece los detalles de la orden en el DTO de respuesta
            responseOrderDTO.setDetallesOrden(responseOrderMenuDTOS);

            // Obtiene el cliente asociado a la orden y actualiza su estado
            Customer customer = order.getCustomerId();
            customer.setStatus(true);
            customerRepository.save(customer);

            // Registra el evento de cancelación en los registros
            logsService.cancelSave(order);

            // Devuelve el DTO de respuesta de la orden
            return responseOrderDTO;
        } catch (Exception e) {
            // Captura cualquier excepción que ocurra y la lanza con un mensaje descriptivo
            throw new Exception(e.getMessage());
        }

    }

    public List<FinishOrderDTO> findAllEndTime() throws Exception{
        try {
            List<Order> orders = orderRepository.findAll();
            List<FinishOrderDTO> orderDTOS = new ArrayList<>();

            for(Order order : orders){

                LocalDateTime startTime = logsRepository.findByOrderLogIdAndStatus(order,OrderStatus.EARRING).getStartTime();
                LocalDateTime endTime = logsRepository.findByOrderLogIdAndStatus(order,OrderStatus.READY).getStartTime();

                Duration duration = Duration.between(startTime,endTime);
                Long time = duration.toMinutes();
                FinishOrderDTO finishOrderDTO = orderMapper.toFinishDTO(order);
                finishOrderDTO.setTiempo(time);
                orderDTOS.add(finishOrderDTO);
            }
            return orderDTOS;
        }catch (Exception e){
            throw new Exception(e.getMessage());

        }
    }

}