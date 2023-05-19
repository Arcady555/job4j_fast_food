package ru.dish.service;

import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SimpleKafkaProducerService implements KafkaProducerService {
    private KafkaTemplate<Integer, String> kafkaTemplate;

    @Override
    public void sendToOrder(int id, String str) {
        kafkaTemplate.send("from_dish_to_order", id, str);
    }

    @Override
    public void sendToAdmin(int id, int amount) {
        kafkaTemplate.send("from_dish_to_admin", id, Integer.toString(amount));
    }
}