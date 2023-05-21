package ru.admin.controller;

import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;
import ru.admin.service.DishService;

@EnableKafka
@Controller
@AllArgsConstructor
public class KafkaConsumerController {
}