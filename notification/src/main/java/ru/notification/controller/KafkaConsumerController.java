package ru.notification.controller;

import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.notification.service.SimpleNotificationService;

@EnableKafka
@Controller
@AllArgsConstructor
@RequestMapping("notification")
public class KafkaConsumerController {
    private final SimpleNotificationService service;

    @KafkaListener(topics = "notification")
    public void msgListener(ConsumerRecord<Integer, String> record) {
        service.save(record.key(), record.value());
    }
}