package com.example.restaurantepragma.services;

import com.example.restaurantepragma.dto.menu.MenuStateDTO;
import com.example.restaurantepragma.dto.menu.MenuUpdateDTO;
import com.example.restaurantepragma.dto.menu.ResponseMenuDTO;
import com.example.restaurantepragma.entities.Employee;
import com.example.restaurantepragma.entities.Menu;
import com.example.restaurantepragma.maps.MenuMapper;
import com.example.restaurantepragma.repository.EmployeeRepository;
import com.example.restaurantepragma.repository.MenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MenuServiceTest {

    @InjectMocks
    private MenuService menuService;

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private MenuMapper menuMapper;

    @Mock
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveMenu() throws Exception {
        // Mocking data and behavior
        Menu menu = new Menu();
        Long employeeId = 1L;
        String password = "password";
        Optional<Employee> employee = Optional.of(new Employee());

        when(employeeRepository.findById(anyLong())).thenReturn(employee);
        when(menuRepository.existsByNameMenuAndFranchise(anyString(), anyString())).thenReturn(false);
        when(menuRepository.save(any())).thenReturn(menu);
        when(menuMapper.toMenuDTO(menu)).thenReturn(new ResponseMenuDTO());

        // Test the method
        ResponseMenuDTO responseMenuDTO = menuService.save(menu, employeeId, password);

        // Assertions
        assertNotNull(responseMenuDTO);
        // Add more assertions based on the expected behavior
    }

    @Test
    public void testFindAllMenus() throws Exception {
        // Mocking data and behavior
        List<Menu> menus = new ArrayList<>();
        when(menuRepository.findAll()).thenReturn(menus);

        // Test the method
        List<ResponseMenuDTO> responseMenuDTOS = menuService.findAll();

        // Assertions
        assertNotNull(responseMenuDTOS);
        // Add more assertions based on the expected behavior
    }

    @Test
    public void testUpdateMenu() throws Exception {
        // Mocking data and behavior
        MenuUpdateDTO menuUpdateDTO = new MenuUpdateDTO();
        Long id = 1L;
        Long employeeId = 2L;
        String password = "password";
        Optional<Menu> menuOptional = Optional.of(new Menu());
        Optional<Employee> employee = Optional.of(new Employee());

        when(menuRepository.findById(anyLong())).thenReturn(menuOptional);
        when(employeeRepository.findById(anyLong())).thenReturn(employee);
        when(menuRepository.save(any())).thenReturn(new Menu());
        when(menuMapper.toMenuDTO(any())).thenReturn(new ResponseMenuDTO());

        // Test the method
        ResponseMenuDTO responseMenuDTO = menuService.update(menuUpdateDTO, id, employeeId, password);

        // Assertions
        assertNotNull(responseMenuDTO);
        // Add more assertions based on the expected behavior
    }

    @Test
    public void testUpdateMenuState() throws Exception {
        // Mocking data and behavior
        Long id = 1L;
        Long employeeId = 2L;
        String password = "password";
        Optional<Menu> menuOptional = Optional.of(new Menu());
        Optional<Employee> employee = Optional.of(new Employee());

        when(menuRepository.findById(anyLong())).thenReturn(menuOptional);
        when(employeeRepository.findById(anyLong())).thenReturn(employee);
        when(menuRepository.save(any())).thenReturn(new Menu());
        when(menuMapper.toMenuState(any())).thenReturn(new MenuStateDTO());

        // Test the method
        MenuStateDTO menuStateDTO = menuService.updateState(id, employeeId, password);

        // Assertions
        assertNotNull(menuStateDTO);
        // Add more assertions based on the expected behavior
    }

    @Test
    public void testFindPlatesForCategoryAndFranchise() throws Exception {
        // Mocking data and behavior
        String category = "Main Course";
        String franchise = "Example Franchise";
        int numberRegister = 10;
        int page = 1;
        Pageable pageable = PageRequest.of((page - 1), numberRegister);
        Page<Menu> menuPage = new PageImpl<>(new ArrayList<>());

        when(menuRepository.findByCategoryAndFranchise(anyString(), anyString(), any())).thenReturn(menuPage);
        when(menuMapper.toMenuDTO(any())).thenReturn(new ResponseMenuDTO());

        // Test the method
        Page<ResponseMenuDTO> responseMenuPage = menuService.findPlatesForCategotyAndFranchise(category, franchise, numberRegister, page);

        // Assertions
        assertNotNull(responseMenuPage);
        // Add more assertions based on the expected behavior
    }
}
