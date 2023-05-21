package ru.admin.service;

import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@EnableKafka
public class SimpleKafkaProducerService implements KafkaProducerService {
    private KafkaTemplate<Integer, String> kafkaTemplate;
}
