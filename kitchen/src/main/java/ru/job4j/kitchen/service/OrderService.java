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
    private final OrderRepository orderRepository;
    private final DishService dishService;
    private final StatusService statusService;

    @Autowired
    private KafkaTemplate<Integer, String> kafkaTemplate;

    public OrderService(OrderRepository orderRepository, DishService dishService, StatusService statusService) {
        this.orderRepository = orderRepository;
        this.dishService = dishService;
        this.statusService = statusService;
    }

    public void save(Order order) {
        orderRepository.save(order);
    }

    public Order findById(int id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
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

    public void msgFromOrder(ConsumerRecord<Integer, String> record) {
        Order order = new Order();
        order.setId(record.key());
        order.setStatus(statusService.findById(1));
        List<Dish> dishes = getDishesFromRecord(record.value());
        order.setDishes(dishes);
        sendToDish(order);
    }

    public void sendToDish(Order order) {
        kafkaTemplate.send("from_kitchen_to_dish", order.getId(), order.getDishes().toString());
    }

    public void msgFromDish(ConsumerRecord<Integer, String> record) {
        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        System.out.println(record.value());
        Order order = new Order();
        Status status;
        List<Dish> dishes;
        order.setId(record.key());
        if (!"null".equals(record.value())) {
            dishes = getDishesFromRecord(record.value());
            status = statusService.findById(3);
        } else {
            dishes = null;
            status = statusService.findById(2);
        }
        order.setDishes(dishes);
        order.setStatus(status);
        save(order);
        sendToOrder(order);
    }

    public void sendToOrder(Order order) {
        kafkaTemplate.send("from_kitchen_to_order", order.getId(), order.toString());
    }

    private List<Dish> getDishesFromRecord(String str) {
        List<Dish> dishes = new ArrayList<>();
        String[] array = str.substring(1, str.length() - 1).split(",");
        for (String element : array) {
            String name = element.replaceAll(" ", "");
            Dish dish;
            if (!"Блюдо закончилось!".equals(name) && !"Блюдо не найдено!".equals(name)) {
                dish = dishService.findByName(name);
            } else {
                dish = new Dish();
                dish.setName(name);
            }
            dishes.add(dish);
        }
        return dishes;
    }
}