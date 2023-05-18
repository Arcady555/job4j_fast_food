package ru.kitchen.service;

import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SimpleKafkaProducerService implements KafkaProducerService {
    private final KafkaTemplate<Integer, String> kafkaTemplate;

    @Override
    public void sendToDelivery(int id, String str) {
        kafkaTemplate.send("from_kitchen_to_delivery", id, str);
    }

    @Override
    public void sendToNotification(int id, String str) {
        kafkaTemplate.send("from_kitchen_to_notification", id, str);
    }
}