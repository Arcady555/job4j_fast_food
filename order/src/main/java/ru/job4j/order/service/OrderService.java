package ru.job4j.order.service;

import lombok.Data;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.job4j.order.dto.OrderDto;
import ru.job4j.order.model.Order;
import ru.job4j.order.model.Product;
import ru.job4j.order.model.Status;
import ru.job4j.order.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Service
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

    public void save(Order order) {
        orders.save(order);
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

    public void sendToNote(Integer orderId, Order order) {
        Status status = statuses.findById(1);
        order.setStatus(status);
        kafkaTemplateS.send("messengers", orderId, order.getStatus().getName());
    }

    public void sendToKitchen(Integer orderId, OrderDto order) {
        order = new OrderDto();
        creationOrderTrain(order);
        kafkaTemplateO.send("preorder", orderId, order);
    }

    public void msgFromKitchen(ConsumerRecord<Integer, Integer> record) {
        int id = record.key();
        Order order = findById(id);
        Status status = statuses.findById(record.value());
        order.setStatus(status);
        orders.save(order);
    }

    private void creationOrderTrain(OrderDto order) {
        Status status = new Status();
        status.setId(1);
        status.setName("принят");
        order.setStatus(status);
        List<Product> productSet = new ArrayList<>();
        Product product1 = new Product();
        product1.setName("молоко");
        Product product2 = new Product();
        product2.setName("мясо");
        productSet.add(product1);
        productSet.add(product2);
        order.setProducts(productSet);
    }
}
