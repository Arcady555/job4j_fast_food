package ru.client.service;

import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.domain.model.Order;

@Service
@AllArgsConstructor
public class SimpleKafkaProducerService implements KafkaProducerService {
    private final KafkaTemplate<Integer, String> kafkaTemplate;

    @Override
    public void sendToOrder(Order order) {
        kafkaTemplate.send("from_client_to_order", order.getId(), order.getStatus().toString());
    }

    @Override
    public void sendToNotification(Order order) {
        kafkaTemplate.send("notification", order.getId(), order.getStatus().toString());
    }
}