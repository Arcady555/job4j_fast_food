package ru.dish.service;

import ru.domain.model.Order;

public interface OrderService {

    Order findById(int id);

    void save(Order order);
}
