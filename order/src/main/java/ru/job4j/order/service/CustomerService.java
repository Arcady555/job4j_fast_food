package ru.job4j.order.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import ru.job4j.order.repository.CustomerRepository;

@Data
@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
   /*
    public Order createOrder(Order order) {

    } */
}
