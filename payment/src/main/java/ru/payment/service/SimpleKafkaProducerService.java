package ru.payment.service;

import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SimpleKafkaProducerService implements KafkaProducerService {
    private KafkaTemplate<Integer, String> kafkaTemplate;

    @Override
    public void sendToOrder(int id, String str) {
        kafkaTemplate.send("from_payment_to_order", id, str);
    }
}