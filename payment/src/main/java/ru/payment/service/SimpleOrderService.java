package ru.payment.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.domain.model.Order;
import ru.domain.model.Status;
import ru.payment.repository.OrderRepository;

import java.util.Optional;

@AllArgsConstructor
@Service
public class SimpleOrderService implements OrderService {
    private final KafkaProducerService kafkaProducerService;
    private final OrderRepository orderRepository;

    @Override
    public void msgFromOrder(int id, String str) {
        if ("ожидает оплату".equals(str)) {
            Order order = findById(id);
            /**
             * Запуск оплаты заказа
             */
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            order.setStatus(Status.PAYED);
            orderRepository.save(order);
            str = Status.PAYED.toString();
            kafkaProducerService.sendToOrder(id, str);
        }
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