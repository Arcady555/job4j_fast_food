package ru.dish.controller;

import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;
import ru.dish.service.DishService;

@EnableKafka
@Controller
@AllArgsConstructor
public class KafkaConsumerController {
    private final DishService dishService;

    @KafkaListener(topics = "from_order_to_dish")
    public void msgFromOrder(ConsumerRecord<Integer, String> record) {
        dishService.msgFromOrder(record.key());
    }
}
