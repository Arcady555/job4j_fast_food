package ru.kitchen.repository;

import org.springframework.data.repository.CrudRepository;
import ru.domain.model.Order;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Integer> {
    @Override
    List<Order> findAll();
}
