package ru.job4j.kitchen.controller;

import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.kitchen.service.OrderService;

@EnableKafka
@Controller
@RequestMapping("kitchen")
@AllArgsConstructor
public class KafkaKitchenController {
    private final OrderService orders;

    @KafkaListener(topics = "preorder")
    public void msgFromOrder(ConsumerRecord<Integer, String> record) {
        orders.msgFromOrder(record);
    }

    @PostMapping("/order")
    public void sendToOrder(Integer orderId, String statusName) {
        orders.sendToOrder(orderId, statusName);
    }
}