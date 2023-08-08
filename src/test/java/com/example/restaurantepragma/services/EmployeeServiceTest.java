package com.example.restaurantepragma.services;

import com.example.restaurantepragma.dto.employee.ResponseEmployeeDTO;
import com.example.restaurantepragma.entities.Employee;
import com.example.restaurantepragma.maps.EmployeeMapper;
import com.example.restaurantepragma.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveEmployee() throws Exception {
        // Mocking data and behavior
        Employee employee = new Employee();
        when(employeeRepository.save(any())).thenReturn(employee);
        when(employeeMapper.toEmployeeDTO(employee)).thenReturn(new ResponseEmployeeDTO());

        // Test the method
        ResponseEmployeeDTO responseEmployeeDTO = employeeService.save(employee);

        // Assertions
        assertNotNull(responseEmployeeDTO);
        // Add more assertions based on the expected behavior
    }

    @Test
    public void testFindAllEmployees() throws Exception {
        // Mocking data and behavior
        List<Employee> employees = new ArrayList<>();
        when(employeeRepository.findAll()).thenReturn(employees);

        // Test the method
        List<ResponseEmployeeDTO> responseEmployeeDTOS = employeeService.findAll();

        // Assertions
        assertNotNull(responseEmployeeDTOS);
        // Add more assertions based on the expected behavior
    }
}
