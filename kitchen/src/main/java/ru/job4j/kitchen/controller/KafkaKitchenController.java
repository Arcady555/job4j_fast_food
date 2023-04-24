package ru.job4j.kitchen.controller;

import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.kitchen.model.Order;
import ru.job4j.kitchen.service.DishService;
import ru.job4j.kitchen.service.OrderService;

@EnableKafka
@Controller
@RequestMapping("kitchen")
@AllArgsConstructor
public class KafkaKitchenController {
    private final OrderService orderService;
    private final DishService dishService;

    @KafkaListener(topics = "from_order_to_kitchen")
    public void msgFromOrder(ConsumerRecord<Integer, String> record) {
        orderService.msgFromOrder(record);
    }

    @PostMapping("/order")
    public void sendToOrder(Order order) {
        orderService.sendToOrder(order);
    }

    @KafkaListener(topics = "from_dish_to_kitchen")
    public void msgFromDish(ConsumerRecord<Integer, String> record) {
        orderService.msgFromDish(record);
    }

    @PostMapping("/kitchen")
    public void sendToDish(Order order) {
        orderService.sendToDish(order);
    }
}