package ru.job4j.kitchen.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.job4j.kitchen.model.Dish;
import ru.job4j.kitchen.model.Order;
import ru.job4j.kitchen.model.Status;
import ru.job4j.kitchen.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orders;
    private final DishService dishService;
    private final StatusService statuses;

    @Autowired
    private KafkaTemplate<Integer, String> kafkaTemplate;

    public OrderService(OrderRepository orders, DishService dishService, StatusService statuses) {
        this.orders = orders;
        this.dishService = dishService;
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
        order.setStatus(statuses.findById(1));
        List<Dish> dishes = getDishesFromRecord(record.value());
        if (dishes != null) {
            order.setDishes(dishes);
            order.setStatus(statuses.findById(5));
            save(order);
        } else {
            order.setStatus(statuses.findById(2));
            save(order);
        }
        sendToOrder(order.getId(), order.getStatus().getName());
    }

    private List<Dish> getDishesFromRecord(String str) {
        List<Dish> dishes = new ArrayList<>();
        String[] array = str.substring(1, str.length() - 1).split(",");
        for (String element : array) {
            String name = element.replaceAll(" ", "");
            Dish dish = dishService.findByName(name);
            if ("Продукт закончился!".equals(dish.getName()) || "Продукт не найден!".equals(dish.getName())) {
                return null;
            }
            int amount = dish.getAmount();
            dish.setAmount(amount - 1);
            dishService.save(dish);
            dishes.add(dish);
        }
        return dishes;
    }
}