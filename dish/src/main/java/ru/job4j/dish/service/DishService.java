package ru.job4j.dish.service;

import lombok.Data;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.job4j.dish.model.Dish;
import ru.job4j.dish.repository.DishRepository;

import java.util.ArrayList;
import java.util.List;
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

    public List<Dish> findAll() {
        return dishRepository.findAll();
    }

    public Dish findById(int id) {
        Optional<Dish> optionalDish = dishRepository.findById(id);
        Dish dish = new Dish();
        if (optionalDish.isPresent()) {
            dish = optionalDish.get();
        } else {
            dish.setName("Блюдо не найдено!");
        }
        return dish;
    }

    public Dish findByName(String name) {
        Optional<Dish> optionalDish = dishRepository.findByName(name);
        Dish dish = new Dish();
        if (optionalDish.isPresent()) {
            dish = optionalDish.get();
            if (dish.getAmount() < 1) {
                dish.setName("Блюдо закончилось!");
            }
        } else {
            dish.setName("Блюдо не найдено!");
        }
        return dish;
    }

    public void msgFromKitchen(ConsumerRecord<Integer, String> record) {
        List<Dish> dishes = getDishesFromKitchen(record.value());
        sendTOAdmin(0, dishes.toString());
        sendToKitchen(record.key(), dishes.toString());
    }

    public void msgFromAdmin(ConsumerRecord<Integer, String> record) {
        Dish dish = getDishFromAdmin(record);
        if ("Удалить".equals(dish.getName())) {
            dishRepository.delete(dish);
        } else {
            dishRepository.save(dish);
        }
    }

    public void sendToKitchen(int id, String dishes) {
        kafkaTemplate.send("from_dish_to_kitchen", id, dishes);
    }

    public void sendTOAdmin(int id, String dishes) {
        kafkaTemplate.send("from_dish_to_admin", id, dishes);
    }

    private List<Dish> getDishesFromKitchen(String str) {
        System.out.println(str);
        List<Dish> dishList = new ArrayList<>();
        boolean  dishExist = false;
        String[] array = str.substring(1, str.length() - 1).split(", ");
        for (String element : array) {
            Dish dish = findByName(element);
            if (!"Блюдо закончилось!".equals(dish.getName()) || !"Блюдо не найдено!".equals(dish.getName())) {
                dishExist = true;
                int amount = dish.getAmount();
               // dish.setId(0);
                dish.setAmount(amount - 1);
                dishRepository.save(dish);
            }
            dishList.add(dish);
        }
        if (!dishExist) {
            dishList = null;
        }
        return dishList;
    }

    private Dish getDishFromAdmin(ConsumerRecord<Integer, String> record) {
        Dish dish = new Dish();
        dish.setId(record.key());
        String[] dishArray = record.value().split(", ");
        String name = dishArray[0];
        int amount = Integer.parseInt(dishArray[1]);
        dish.setName(name);
        dish.setAmount(amount);
        return dish;
    }
}