package org.topteam1.repository;

import org.topteam1.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {

    private final ArrayList<Customer> customers = new ArrayList<>();

    /**
     * Метод для добавления покупателя
     *
     * @param id           хранит ID покупателя
     * @param name         хранит имя покупателя
     * @param customerType хранит тип покупателя
     */
    public void addCustomer(Integer id, String name, String customerType) {
        Customer customer = new Customer();
        customers.add(customer);
    }

    /**
     * Метод для отображения списка покупателей
     *
     * @return возвращает список всех покупателей
     */
    public List<Customer> showAllCustomers() {
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
