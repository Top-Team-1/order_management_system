package org.topteam1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.topteam1.Exceptions.CustomerNotFoundException;
import org.topteam1.model.Customer;
import org.topteam1.model.CustomerType;
import org.topteam1.repository.CustomerRepository;

import java.util.List;

public class CustomerService {

    private static final Logger log = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Метод добавляет нового покупателя в систему
     *
     * @param name хранит имя покупателя
     * @return возвращает покупателя
     */
    public Customer addCustomer(String name) {
        log.info("Попытка добавить покупателя: name={}", name);
        Customer newCustomer = new Customer(null, name);
        log.info("Покупатель успешно добавлен: {}", newCustomer.getName());
        return customerRepository.save(newCustomer);
    }

    /**
     * Метод для работы со списком покупателей
     *
     * @return возвращает список покупателей
     */
    public List<Customer> getAllCustomers() {
        log.info("Поиск всех покупателей");
        return customerRepository.findAll().stream()
                .map(Customer::new)
                .toList();
    }

    /**
     * Метод для поиска покупателя по ID
     *
     * @param id хранит заданный ID
     * @return возвращает покупателя с заданным ID
     */
    public Customer getCustomerById(long id) {
        log.info("Идет поиск покупателя по id: {}", id);
        return getAllCustomers().stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElseThrow(() -> new CustomerNotFoundException("Покупатель с ID " + id + " не найден!"));
    }


    public Customer checkCustomerType(Customer customer) {
        customer.setCountOrder(customer.getCountOrder() + 1);
        if (customer.getCountOrder() >= 4) {
            customer.setCustomerType(CustomerType.VIP);
        } else if (customer.getCountOrder() > 1 || customer.getCountOrder() < 4) {
            customer.setCustomerType(CustomerType.REGULAR);
        }
        return customerRepository.save(customer);
    }
}
