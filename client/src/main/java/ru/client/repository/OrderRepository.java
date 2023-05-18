package ru.client.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.domain.model.Order;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {

    @Override
    List<Order> findAll();
}