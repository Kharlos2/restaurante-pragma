package com.example.restaurantepragma.services;

import com.example.restaurantepragma.dto.customer.CustomerLogsDTO;
import com.example.restaurantepragma.dto.customer.ResponseCustomerDTO;
import com.example.restaurantepragma.dto.logs.ResponseLogsDTO;
import com.example.restaurantepragma.dto.order.OrderLogsDTO;
import com.example.restaurantepragma.entities.Customer;
import com.example.restaurantepragma.entities.Logs;
import com.example.restaurantepragma.entities.Order;
import com.example.restaurantepragma.enums.CustomerResponses;
import com.example.restaurantepragma.maps.CustomerMapper;
import com.example.restaurantepragma.maps.LogsMapper;
import com.example.restaurantepragma.maps.OrderMapper;
import com.example.restaurantepragma.repository.CustomerRepository;
import com.example.restaurantepragma.repository.LogsRepositoy;
import com.example.restaurantepragma.repository.OrderRepository;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    // Inyección de dependencias con la anotación @Autowired
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private LogsMapper logsMapper;
    @Autowired
    private LogsRepositoy logsRepositoy;
    private OrderRepository orderRepository;

    // Método para guardar un objeto Customer en la base de datos
    public ResponseCustomerDTO save(Customer customer)throws Exception{
        try {
            // Guarda el objeto Customer en la base de datos y retorna el resultado
            // Luego, convierte el resultado en un ResponseCustomerDTO usando el CustomerMapper
            return customerMapper.toCustomerDTO(customerRepository.save(customer));
        }catch (Exception e){
            // En caso de error, lanza una excepción con el mensaje del error origina
            throw new Exception(e.getMessage());
        }
    }

    // Método para obtener todos los objetos Customer almacenados en la base de datos
    public List<ResponseCustomerDTO> findAll()throws Exception{
        try {
            // Obtiene todos los objetos Customer de la base de datos y los guarda en una lista
            // Luego, convierte la lista de Customer en una lista de ResponseCustomerDTO usando el CustomerMapper
            return customerMapper.toCustomersDTO(customerRepository.findAll());
        }catch (Exception e){
            // En caso de error, lanza una excepción con el mensaje del error original
            throw new Exception(e.getMessage());
        }
    }
    public CustomerLogsDTO findLogsById(Long id)throws Exception{
        try {
            Optional<Customer> customerOptional = customerRepository.findById(id);
            if (customerOptional.isEmpty())throw new Exception(CustomerResponses.NOT_FOUNT.getMessage());

            Customer customer = customerOptional.get();
            List<Order> orders = customer.getOrders();
            List<Logs> logs = logsRepositoy.findAll();

            List<OrderLogsDTO> orderLogsDTOs = new ArrayList<>();
            for (Order order : orders) {
                OrderLogsDTO orderLogsDTO = new OrderLogsDTO();
                orderLogsDTO.setId(order.getId()); // Establecer el ID de la orden

                List<ResponseLogsDTO> responseLogsDTOs = new ArrayList<>();
                for (Logs log : logs) {
                    // Aquí debes encontrar la forma de obtener el registro correspondiente
                    // entre la orden y el log específico
                    // Utiliza algún método que se ajuste a tus relaciones y mapeos
                    ResponseLogsDTO responseLogsDTO = logsMapper.toLogsDTO(log);
                    responseLogsDTOs.add(responseLogsDTO);
                }
                orderLogsDTO.setRegistros(responseLogsDTOs); // Establecer la lista de registros para esta orden
                orderLogsDTOs.add(orderLogsDTO);
            }

            CustomerLogsDTO customerLogsDTO = customerMapper.toCustomerLogsDTO(customer);
            customerLogsDTO.setOrdenes(orderLogsDTOs); // Establecer la lista de órdenes con registros

            return customerLogsDTO;

        }catch (Exception e){
            throw new Exception(e.getMessage());

        }
    }
}
