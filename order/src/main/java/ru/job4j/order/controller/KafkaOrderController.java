package ru.job4j.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.order.model.Order;
import ru.job4j.order.model.Status;

@RestController
@RequestMapping("order")
public class KafkaOrderController {
    @Autowired
    private KafkaTemplate<Integer, String> kafkaTemplate;

    @PostMapping
    public void sendOrder(Integer orderId, Order order) {
        Status status = new Status();
        status.setName("принят");
        order.setStatus(status);
        kafkaTemplate.send("messengers", orderId, order.getStatus().getName());
    }
}