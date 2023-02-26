package ru.job4j.order.controller;

import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;
import ru.job4j.order.model.Order;
import ru.job4j.order.service.OrderService;

@EnableKafka
@AllArgsConstructor
@RestController
@RequestMapping("/order")
public class OrderController {
    private OrderService orderService;

    @PostMapping("/new-order")
    public ResponseEntity<String> createOrder(Order order) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("order_status")
    public ResponseEntity<String> checkStatus(int orderId) {
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/kitchen")
    public void sendToKitchen(@RequestBody Order order) {
        orderService.saveOut(order);
    }

    @KafkaListener(topics = "cooked_order")
    public void msgFromKitchen(ConsumerRecord<Integer, String> record) {
        orderService.saveIn(record);
    }
}
