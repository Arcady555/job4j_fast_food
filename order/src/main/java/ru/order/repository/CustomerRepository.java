package ru.order.repository;

import org.springframework.data.repository.CrudRepository;
import ru.domain.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
}
