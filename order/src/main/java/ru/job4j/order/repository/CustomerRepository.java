package ru.job4j.order.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.order.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
}
