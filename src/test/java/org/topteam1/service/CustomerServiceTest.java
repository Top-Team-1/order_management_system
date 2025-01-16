package org.topteam1.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.topteam1.model.Customer;
import org.topteam1.model.CustomerType;
import org.topteam1.repository.CustomerRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTest {
    private CustomerService customerService;
    private Path newFilePath;

    @BeforeEach
    void setUp() throws IOException {

        newFilePath = Path.of("src/test/java/org/topteam1/customer_test.txt");
        Files.createFile(newFilePath);
        CustomerRepository customerRepository = new CustomerRepository(newFilePath.toString());
        customerService = new CustomerService(customerRepository);
    }

    @AfterEach
    void tearDown() throws IOException {

        Files.deleteIfExists(newFilePath);
        Files.deleteIfExists(Path.of("src/test/java/org/topteam1/customer_test.txt_id"));
    }

    @Test
    void addCustomer_Successful_Addition_Customer() {

        //given
        CustomerType customerType = CustomerType.NEW;

        //when
        Customer customer = customerService.addCustomer("Иннокентий");

        //then
        assertNotNull(customer);
        assertEquals("Иннокентий", customer.getName());
        assertEquals(customerType, customer.getCustomerType());
        assertEquals(1, customerService.getAllCustomers().size());
    }

    @Test
    void getAllCustomers_Show_Return_CustomerList() {

        //given
        customerService.addCustomer("Валерия");

        //when
        List<Customer> customers = customerService.getAllCustomers();

        //then
        assertEquals("Валерия", customers.get(0).getName());
    }

    @Test
    void getCustomerById_Show_Return_Customer() {

        //given
        Customer createCustomer = customerService.addCustomer("Сергей");
        createCustomer.setId(1L);

        //when
        Customer foundCustomer = customerService.getCustomerById(Math.toIntExact(createCustomer.getId()));

        //then
        assertNotNull(foundCustomer);
        assertEquals(createCustomer.getId(), foundCustomer.getId());
        assertEquals("Сергей", foundCustomer.getName());
    }

    @Test
    void checkCustomerType() {

        //given
        Customer addedCustomer = customerService.addCustomer("Евгений");
        Customer customer = new Customer(null, "Евгений");
        customer.setCustomerType(CustomerType.VIP);

        //when
        customerService.checkCustomerType(addedCustomer);
        addedCustomer.setCustomerType(customer.getCustomerType());

        //then
        assertEquals("Евгений", addedCustomer.getName());
        assertEquals(CustomerType.VIP, addedCustomer.getCustomerType());
    }

}