package org.topteam1.repository;

import org.topteam1.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {

    private final List<Customer> customers;
    private Integer countId;

    public CustomerRepository() {
        this.customers = new ArrayList<>();
        countId = 0;
    }

    /**
     * Метод для добавления покупателя
     *
     * @param customer содержит данные о покупателе
     * @return возвращает покупателя
     */
    public Customer saveCustomer(Customer customer) {

        customer.setId(++countId);

        if (customers.add(customer)) {
            return customer;
        }
        return null;
    }

    /**
     * Метод для отображения списка покупателей
     *
     * @return возвращает список всех покупателей
     */
    public List<Customer> findCustomer() {
        return customers;
    }

    /**
     * Метод для поиска покупателя по ID
     *
     * @param customerId принимает в себя ID покупателя
     * @return возвращает покупателя с заданным ID
     */
    public Customer findCustomerForId(Integer customerId) {
        return customers.stream().filter(c -> c != null && c.getId() == customerId).findFirst().orElse(null);
    }
}
