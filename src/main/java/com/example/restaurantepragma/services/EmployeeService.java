package com.example.restaurantepragma.services;

import com.example.restaurantepragma.dto.employee.ResponseEmployeeDTO;
import com.example.restaurantepragma.entities.Employee;
import com.example.restaurantepragma.maps.EmployeeMapper;
import com.example.restaurantepragma.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
