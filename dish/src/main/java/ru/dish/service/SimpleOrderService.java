package ru.dish.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dish.repository.OrderRepository;
import ru.domain.model.Order;
import ru.domain.model.Status;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleOrderService implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public Order findById(int id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        Order order = new Order();
        if (optionalOrder.isPresent()) {
            order = optionalOrder.get();
        } else {
            order.setStatus(Status.NOT_FOUND);
        }
        return order;
    }

    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }
}
