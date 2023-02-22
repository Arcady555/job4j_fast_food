package ru.job4j.kitchen.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.kitchen.model.Order;

public interface OrderRepository extends CrudRepository<Order, Integer> {

}
