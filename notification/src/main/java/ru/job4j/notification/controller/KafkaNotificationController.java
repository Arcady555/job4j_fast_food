package ru.job4j.notification.controller;

import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.notification.service.NotificationService;

@EnableKafka
@Controller
@AllArgsConstructor
@RequestMapping("notification")
public class KafkaNotificationController {
    private final NotificationService service;

    @KafkaListener(topics = "messengers")
    public void msgListener(ConsumerRecord<Integer, String> record) {
        service.msgListener(record);
    }
}