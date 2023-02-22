package ru.job4j.order.controller;

import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.order.dto.OrderDto;
import ru.job4j.order.model.Order;
import ru.job4j.order.service.OrderService;

@EnableKafka
@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class KafkaOrderController {
    private final OrderService orders;

    @PostMapping ("/notification")
    public void sendToNote(Integer orderId, Order order) {
        orders.sendToNote(orderId, order);
    }

    @PostMapping("/kitchen")
    public void sendToKitchen(Integer orderId, OrderDto order) {
        orders.sendToKitchen(orderId, order);
    }

    @KafkaListener(topics = "cooked_order")
    public void msgFromKitchen(ConsumerRecord<Integer, Integer> record) {
        orders.msgFromKitchen(record);
    }
}