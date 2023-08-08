package com.example.restaurantepragma.services;

import com.example.restaurantepragma.dto.employee.RankingEmployeeDTO;
import com.example.restaurantepragma.dto.employee.ResponseEmployeeDTO;
import com.example.restaurantepragma.entities.Employee;
import com.example.restaurantepragma.entities.Order;
import com.example.restaurantepragma.enums.OrderStatus;
import com.example.restaurantepragma.maps.EmployeeMapper;
import com.example.restaurantepragma.repository.EmployeeRepository;
import com.example.restaurantepragma.repository.LogsRepository;
import com.example.restaurantepragma.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class EmployeeService {

    // Inyectamos las dependencias necesarias usando la anotación @Autowired
    // Esta anotación permite que Spring inyecte automáticamente una instancia de EmployeeRepository y EmployeeMapper
    // para que podamos utilizarlas en esta clase.

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private LogsRepository logsRepository;

    // Método para guardar un empleado en la base de datos.
    // Recibe un objeto de tipo Employee y retorna un ResponseEmployeeDTO que contiene la información del empleado guardado.
    public ResponseEmployeeDTO save(Employee employee) throws Exception{
        try {
            // Usamos el EmployeeRepository para guardar el objeto Employee en la base de datos.
            // Luego, convertimos el resultado a un ResponseEmployeeDTO usando el EmployeeMapper y lo retornamos.
            return employeeMapper.toEmployeeDTO(employeeRepository.save(employee));
        }catch (Exception e){
            // En caso de que ocurra una excepción durante el proceso, lanzamos una nueva excepción con el mensaje de error.
            throw new Exception(e.getMessage());
        }
    }

    // Método para obtener una lista de todos los empleados almacenados en la base de datos.
    // Retorna una lista de objetos ResponseEmployeeDTO que contienen la información de los empleados encontrados.
    public List<ResponseEmployeeDTO> findAll()throws Exception{
        try {
            // Usamos el EmployeeRepository para obtener una lista de todos los empleados almacenados en la base de datos.
            // Luego, convertimos la lista de empleados a una lista de ResponseEmployeeDTO usando el EmployeeMapper y la retornamos.
            return employeeMapper.toEmployeesDTO(employeeRepository.findAll());
        }catch (Exception e){
            // En caso de que ocurra una excepción durante el proceso, lanzamos una nueva excepción con el mensaje de error.
            throw new Exception(e.getMessage());
        }
    }
    public List<RankingEmployeeDTO> findAllAverages()throws Exception{
        try {
            // Obtener la lista de todos los empleados desde el repositorio
            List<Employee> employees = employeeRepository.findAll();

            // Crear una lista para almacenar los datos de ranking de empleados
            List<RankingEmployeeDTO> rankingEmployeesDTO = employeeMapper.toRankingEmployeesDTO(employees);

            // Iterar a través de cada empleado
            for (Employee employee : employees) {
                int quantityOrders = 0; // Contador de órdenes procesadas por el empleado
                long minutes = 0; // Acumulador de minutos totales para calcular el promedio

                // Obtener todas las órdenes asociadas a este empleado desde el repositorio de órdenes
                List<Order> orders = orderRepository.findAllByEmployeeId(employee);

                // Iterar a través de cada orden del empleado
                for (Order order : orders) {
                    quantityOrders++; // Incrementar el contador de órdenes

                    // Obtener la marca de tiempo de inicio y finalización de la orden desde el repositorio de registros
                    LocalDateTime startTime = logsRepository.findByOrderLogIdAndStatus(order, OrderStatus.EARRING).getStartTime();
                    LocalDateTime endTime = logsRepository.findByOrderLogIdAndStatus(order, OrderStatus.READY).getStartTime();

                    // Calcular la duración de la orden en minutos
                    Duration duration = Duration.between(startTime, endTime);
                    long time = duration.toMinutes();
                    minutes += time; // Acumular el tiempo total en minutos
                }

                // Convertir el empleado en DTO de ranking
                RankingEmployeeDTO employeeDTO = employeeMapper.toRankingEmployeeDTO(employee);

                // Calcular el promedio de tiempo si hay órdenes, de lo contrario, establecerlo como nulo
                if (quantityOrders != 0) {
                    employeeDTO.setPromedio(minutes / quantityOrders);
                } else {
                    employeeDTO.setPromedio(null);
                }

                // Agregar el DTO de empleado al ranking
                rankingEmployeesDTO.add(employeeDTO);
            }

            // Devolver la lista de DTOs de empleados para el ranking
            return rankingEmployeesDTO;
        } catch (Exception e) {
            // Capturar cualquier excepción y relanzarla como una excepción controlada
            throw new Exception(e.getMessage());
        }
    }

}
