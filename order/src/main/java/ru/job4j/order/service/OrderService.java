package ru.job4j.order.service;

import lombok.Data;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.job4j.order.model.Order;
import ru.job4j.order.model.Status;
import ru.job4j.order.repository.OrderRepository;

import java.util.Optional;

@Data
@Service
public class OrderService {
    private final OrderRepository orders;
    private final StatusService statuses;
    @Autowired
    private KafkaTemplate<Integer, String> kafkaTemplate;
    @Autowired
    private KafkaTemplate<Integer, Integer> kafkaTemplate1;


    public OrderService(OrderRepository orders, StatusService statuses) {
        this.orders = orders;
        this.statuses = statuses;
    }

    public void save(Order order) {
        orders.save(order);
    }

    public Order findById(int id) {
        Optional<Order> optionalOrder = orders.findById(id);
        Order order = new Order();
        if (optionalOrder.isPresent()) {
            order = optionalOrder.get();
        } else {
            Status status = new Status();
            status.setName("Заказ не найден!");
            order.setStatus(status);
        }
        return order;
    }

    public void sendToNote(Integer orderId, Order order) {
        Status status = statuses.findById(1);
        order.setStatus(status);
        kafkaTemplate.send("messengers", orderId, order.getStatus().getName());
    }

    public void sendToKitchen(Integer orderId, Order order) {
        Status status = statuses.findById(1);
        order.setStatus(status);
        kafkaTemplate1.send("preorder", orderId, order.getStatus().getId());
    }

    public void msgFromKitchen(ConsumerRecord<Integer, Integer> record) {
        int id = record.key();
        Order order = findById(id);
        Status status = statuses.findById(record.value());
        order.setStatus(status);
        orders.save(order);
    }
}