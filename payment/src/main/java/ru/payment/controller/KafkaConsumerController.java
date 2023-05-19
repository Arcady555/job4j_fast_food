package ru.payment.controller;

import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;
import ru.payment.service.OrderService;

@EnableKafka
@Controller
@AllArgsConstructor
public class KafkaConsumerController {
    private final OrderService orderService;

    @KafkaListener(topics = "from_order_to_payment")
    public void fromOrder(ConsumerRecord<Integer, String> record) {
        orderService.msgFromOrder(record.key(), record.value());
    }
}
