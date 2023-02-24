package ru.job4j.kitchen.service;

import lombok.Data;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.job4j.kitchen.model.Order;
import ru.job4j.kitchen.model.Status;
import ru.job4j.kitchen.repository.OrderRepository;

import java.util.Optional;
import java.util.Random;

@Data
@Service
public class OrderService {
    private final OrderRepository orders;
    private final ProductService foodStock;
    private final StatusService statuses;

    @Autowired
    private KafkaTemplate<Integer, String> kafkaTemplate;

    public OrderService(OrderRepository orders, ProductService foodStock, StatusService statuses) {
        this.orders = orders;
        this.foodStock = foodStock;
        this.statuses = statuses;
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

    public void sendToOrder(Integer orderId, String statusName) {
        kafkaTemplate.send("cooked_order", orderId, statusName);
    }

    public void msgFromOrder(ConsumerRecord<Integer, String> record) {
        Order order = new Order();
        order.setId(record.key());
        Status status = statuses.findByName(getStatusFromJson(record.value()));
        order.setStatus(status);
        save(order);
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Random random = new Random();
        boolean productEnough = random.nextBoolean();
        if (!productEnough) {
            Status statusNo = statuses.findById(2);
            order.setStatus(statusNo);
            save(order);
        } else {
            Status statusNo = statuses.findById(5);
            order.setStatus(statusNo);
            save(order);
        }
        sendToOrder(order.getId(), order.getStatus().getName());
    }

    private String getStatusFromJson(String str) {
        String[] array = str.split(":");
        int i = findI(array);
        String[] statusArray = array[i + 3].split(",");
        return statusArray[0].substring(1, statusArray[0].length() - 1);
    }

    private int findI(String[] array) {
        int rsl = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i].contains("status")) {
                rsl = i;
            }
        }
        return rsl;
    }
}