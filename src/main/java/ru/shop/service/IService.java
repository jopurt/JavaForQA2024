package ru.shop.service;

import ru.shop.model.Customer;

import java.util.List;

public interface IService<M> {
    void save(M entity);

    List<M> findAll();
}
