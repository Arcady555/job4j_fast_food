package ru.order.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SimpleOrderService implements OrderService {
    private final KafkaProducerService kafkaProducerService;

    @Override
    public void msgFromClient(int id, String str) {
        kafkaProducerService.sendToDish(id, str);
    }

    @Override
    public void msgFromDish(int id, String str) {
        if ("ожидает оплату".equals(str.substring(1, str.length() - 2))) {
            kafkaProducerService.sendToPayment(id, str);
        }
    }

    @Override
    public void msgFromPayment(int id, String str) {
        if ("оплачен".equals(str.substring(1, str.length() - 2))) {
            kafkaProducerService.sendToKitchen(id, str);
        }
    }
}