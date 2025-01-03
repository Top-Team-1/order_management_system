package org.topteam1.service;

import org.topteam1.model.Customer;
import org.topteam1.model.CustomerType;
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
     * @param name хранит имя покупателя
     * @return возвращает покупателя
     */
    public Customer addCustomer(String name) {
        Customer newCustomer = new Customer(null, name);
        return customerRepository.save(newCustomer);
    }

    /**
     * Метод для работы со списком покупателей
     *
     * @return возвращает список покупателей
     */
    public List<Customer> getCustomer() {
        return customerRepository.findAll();
    }

    /**
     * Метод для поиска покупателя по ID
     *
     * @param id хранит заданный ID
     * @return возвращает покупателя с заданным ID
     */
    public Customer getCustomerForId(Integer id) {
        return customerRepository.find(id);
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
