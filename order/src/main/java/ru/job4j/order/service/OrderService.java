package ru.job4j.order.service;

import lombok.Data;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.order.dto.OrderDto;
import ru.job4j.order.model.Order;
import ru.job4j.order.model.Status;
import ru.job4j.order.repository.OrderRepository;

import java.util.Optional;

@EnableKafka
@Data
@Service
@RestController
@RequestMapping("/order")
public class OrderService {
    private final OrderRepository orders;
    private final StatusService statuses;
    private final ProductService products;
    @Autowired
    private KafkaTemplate<Integer, String> kafkaTemplateS;
    @Autowired
    private KafkaTemplate<Integer, OrderDto> kafkaTemplateO;

    public OrderService(OrderRepository orders, StatusService statuses, ProductService products) {
        this.orders = orders;
        this.statuses = statuses;
        this.products = products;
    }

    public void saveOut(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setStatus(order.getStatus());
        orders.save(order);
        kafkaTemplateO.send("preorder", order.getId(), orderDto);
        kafkaTemplateS.send("messengers", order.getId(), order.getStatus().getName());

    }

    public void saveIn(Order order) {
        orders.save(order);
        kafkaTemplateS.send("messengers", order.getId(), order.getStatus().getName());

    }

    public Order findById(int id) {
        Optional<Order> optionalOrder = orders.findById(id);
        Order order = new Order();
        if (optionalOrder.isPresent()) {
            order = optionalOrder.get();
        } else {
            Status status = new Status();
            status.setName("Заказ не найден!");
            order.setStatus(status);
        }
        return order;
    }

    @PostMapping("/kitchen")
    private void sendToKitchen(Integer orderId) {
        Order order = new Order();
        order.setId(orderId);
        Status status = new Status();
        status.setId(1);
        status.setName("принят");
        order.setStatus(status);
        saveOut(order);
    }

    @KafkaListener(topics = "cooked_order")
    private void msgFromKitchen(ConsumerRecord<Integer, String> record) {
        int id = record.key();
        Order order = findById(id);
        Status status = statuses.findByName(record.value());
        order.setStatus(status);
        saveIn(order);
    }
}