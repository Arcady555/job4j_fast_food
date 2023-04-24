package ru.job4j.kitchen.service;

import lombok.Data;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.job4j.kitchen.model.Dish;
import ru.job4j.kitchen.model.Order;
import ru.job4j.kitchen.repository.DishRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@EnableKafka
@Service
@Data
public class DishService {
    private final DishRepository dishRepository;
    @Autowired
    private KafkaTemplate<Integer, String> kafkaTemplate;

    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    public void sendToDish(int orderId, String str) {
        kafkaTemplate.send("from_kitchen_to_dish", orderId, str);
    }
/*
    public Order msgFromDish(ConsumerRecord<Integer, String> record) {
        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        System.out.println(record.value());
        Order order = new Order();
        order.setId(record.key());
        List<Dish> dishes = new ArrayList<>();
        String str = record.value();
        if (!"null".equals(str)) {
            String[] array = str.substring(1, str.length() - 1).split(",");
            for (String element : array) {
                String name = element.replaceAll(" ", "");
                Dish dish;
                if (!"Блюдо закончилось!".equals(name) && !"Блюдо не найдено!".equals(name)) {
                    dish = findByName(name);
                } else {
                    dish = new Dish();
                    dish.setName(name);
                }
                dishes.add(dish);
            }
        } else {
            dishes = null;
        }
        order.setDishes(dishes);
        return order;
    } */

    public Dish findByName(String name) {
        Optional<Dish> optionalDish = dishRepository.findByName(name);
        Dish dish = new Dish();
        if (optionalDish.isEmpty()) {
            dish.setName("Блюдо не найдено!");
        } else {
            dish = optionalDish.get();
        }
        return dish;
    }
}