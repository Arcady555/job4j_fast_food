package ru.order.service;

import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SimpleKafkaProducerService implements KafkaProducerService {
    private KafkaTemplate<Integer, String> kafkaTemplate;

    @Override
    public void sendToDish(int id, String str) {
        kafkaTemplate.send("from_order_to_dish", id, str);
    }

    @Override
    public void sendToNotification(int id, String str) {
        kafkaTemplate.send("notification", id, str);
    }

    @Override
    public void sendToPayment(int id, String str) {
        kafkaTemplate.send("from_order_to_payment", id, str);
    }

    @Override
    public void sendToKitchen(int id, String str) {
        kafkaTemplate.send("from_order_to_kitchen", id, str);
    }
}