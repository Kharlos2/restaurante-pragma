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

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeMapper employeeMapper;

    public ResponseEmployeeDTO save(Employee employee) throws Exception{
        try {
            return employeeMapper.toEmployeeDTO(employeeRepository.save(employee));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    public List<ResponseEmployeeDTO> findAll()throws Exception{
        try {
            return employeeMapper.toEmployeesDTO(employeeRepository.findAll());
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

}
