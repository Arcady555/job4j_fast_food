package ru.payment.repository;

import org.springframework.data.repository.CrudRepository;
import ru.domain.model.Order;

public interface OrderRepository extends CrudRepository<Order, Integer> {
}
