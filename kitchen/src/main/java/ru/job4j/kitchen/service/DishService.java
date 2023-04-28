package ru.job4j.kitchen.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.job4j.kitchen.model.Dish;
import ru.job4j.kitchen.repository.DishRepository;

import java.util.Optional;

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