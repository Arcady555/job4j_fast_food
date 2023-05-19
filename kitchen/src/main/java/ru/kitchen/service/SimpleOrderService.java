package ru.kitchen.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.domain.model.Order;
import ru.domain.model.Status;
import ru.kitchen.repository.OrderRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleOrderService implements OrderService  {
    private final OrderRepository orderRepository;
    private final KafkaProducerService kafkaProducerService;

    @Override
    public void msgFromOrder(int id) {
        Order order = findById(id);
        if (Status.PAYED.equals(order.getStatus())) {
            order.setStatus(Status.COOKING);
            orderRepository.save(order);
            kafkaProducerService.sendToNotification(id, order.getStatus().toString());
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            /**
             * Запуск приготовления заказа
            */
            order.setStatus(Status.READY_FOR_DELIVERY);
            orderRepository.save(order);
        }
        kafkaProducerService.sendToDelivery(id, order.getStatus().toString());
        kafkaProducerService.sendToNotification(id, order.getStatus().toString());
    }

    private Order findById(int id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        Order order = new Order();
        if (optionalOrder.isPresent()) {
            order = optionalOrder.get();
        } else {
            order.setStatus(Status.NOT_FOUND);
        }
        return order;
    }
}