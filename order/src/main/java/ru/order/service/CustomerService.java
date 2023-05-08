package ru.order.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import ru.order.repository.CustomerRepository;

@Data
@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
   /*
    public Order createOrder(Order order) {

    } */
}
