package org.topteam1.service;

import org.topteam1.model.Customer;
import org.topteam1.repository.CustomerRepository;

import java.util.List;

public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Метод добавляет нового покупателя в систему
     *
     * @param name         хранит имя покупателя
     * @param customerType хранит тип покупателя
     * @return возвращает покупателя
     */
    public Customer addCustomer(String name, String customerType) {
        Customer newCustomer = new Customer(name, customerType);
        return customerRepository.saveCustomer(newCustomer);
    }

    /**
     * Метод для работы со списком покупателей
     *
     * @return возвращает список покупателей
     */
    public List<Customer> getCustomer() {
        return customerRepository.findCustomer();
    }

    /**
     * Метод для поиска покупателя по ID
     *
     * @param id хранит заданный ID
     * @return возвращает покупателя с заданным ID
     */
    public Customer getCustomerForId(Integer id) {
        return customerRepository.findCustomerForId(id);
    }
}
