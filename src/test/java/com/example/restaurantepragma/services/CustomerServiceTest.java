package com.example.restaurantepragma.services;



import com.example.restaurantepragma.dto.customer.ResponseCustomerDTO;
import com.example.restaurantepragma.entities.Customer;
import com.example.restaurantepragma.maps.CustomerMapper;
import com.example.restaurantepragma.repository.CustomerRepository;
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
public class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveCustomer() throws Exception {
        // Mocking data and behavior
        Customer customer = new Customer();
        when(customerRepository.save(any())).thenReturn(customer);
        when(customerMapper.toCustomerDTO(customer)).thenReturn(new ResponseCustomerDTO());

        // Test the method
        ResponseCustomerDTO responseCustomerDTO = customerService.save(customer);

        // Assertions
        assertNotNull(responseCustomerDTO);
        // Add more assertions based on the expected behavior
    }

    @Test
    public void testFindAllCustomers() throws Exception {
        // Mocking data and behavior
        List<Customer> customers = new ArrayList<>();
        when(customerRepository.findAll()).thenReturn(customers);

        // Test the method
        List<ResponseCustomerDTO> responseCustomerDTOS = customerService.findAll();

        // Assertions
        assertNotNull(responseCustomerDTOS);
        // Add more assertions based on the expected behavior
    }
}
