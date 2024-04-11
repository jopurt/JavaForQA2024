package ru.shop.repository;

import ru.shop.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerRepository implements IRepository<Customer> {
    List<Customer> customers=new ArrayList<>();

    @Override
    public void save(Customer customer) {
        customers.add(customer);
    }

    @Override
    public List<Customer> findAll() {
        return customers;
    }
}
