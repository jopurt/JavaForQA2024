package ru.shop.service;


import ru.shop.model.Customer;
import ru.shop.repository.CustomerRepository;
import ru.shop.repository.IRepository;

import java.util.List;


public class CustomerService implements IService<Customer> {

    private final IRepository<Customer> repository;

    public CustomerService(IRepository<Customer> repository) {
        this.repository = repository;
    }

    @Override
    public void save(Customer customer) {
        repository.save(customer);
    }

    @Override
    public List<Customer> findAll() {
        return repository.findAll();
    }

}
