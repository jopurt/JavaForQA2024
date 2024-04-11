package ru.shop.service;

import ru.shop.exception.BadOrderCountException;
import ru.shop.model.Customer;
import ru.shop.model.Order;
import ru.shop.model.Product;
import ru.shop.repository.OrderRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public void save(Order product) {
        repository.save(product);
    }

    public List<Order> findAll() {
        return repository.findAll();
    }

    public void add(Customer customer, Product product, long count) {
        if(count<=0){
            throw new BadOrderCountException();
        }
       Order order = new Order(
               UUID.randomUUID(),
               customer.getId(),
               product.getId(),
               count,
               product.getCost()*count
       );
        repository.save(order);
    }

    public List<Order> findByCustomer(Customer customer) {
        List<Order> result = new ArrayList<>();
        for (Order order : repository.findAll()) {
            if(order.getCustomerId()==customer.getId()){
                result.add(order);
            }
        }
        return result;
    }

    public long getTotalCustomerAmount(Customer customer) {
        long cost=0;
        for (Order order : findByCustomer(customer)) {
            cost=cost + order.getAmount();
        }
        return cost;
    }
}
