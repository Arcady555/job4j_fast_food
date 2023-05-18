package ru.client.service;

import ru.domain.model.Order;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OrderService {
    boolean save(Order order, HttpServletRequest req);

    Order findById(int id);

    List<Order> findAll();
}