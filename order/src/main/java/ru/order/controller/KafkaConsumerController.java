package ru.order.controller;

import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;
import ru.order.service.OrderService;

@EnableKafka
@Controller
@AllArgsConstructor
public class KafkaConsumerController {
    private final OrderService orderService;

    @KafkaListener(topics = "from_client_to_order")
    public void fromClient(ConsumerRecord<Integer, String> record) {
        orderService.msgFromClient(record.key(), record.value());
    }

    @KafkaListener(topics = "from_dish_to_order")
    public void fromDish(ConsumerRecord<Integer, String> record) {
        orderService.msgFromDish(record.key(), record.value());
    }

    @KafkaListener(topics = "from_payment_to_order")
    public void fromPayment(ConsumerRecord<Integer, String> record) {
        orderService.msgFromPayment(record.key(), record.value());
    }
}
