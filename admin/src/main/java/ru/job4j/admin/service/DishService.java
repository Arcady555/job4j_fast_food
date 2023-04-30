package ru.job4j.admin.service;

import lombok.Data;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.job4j.admin.model.Dish;
import ru.job4j.admin.repository.DishRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Data
@EnableKafka
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

    public void msgFromDish(ConsumerRecord<Integer, String> record) {
        List<Dish> dishList = getDishesFromDish(record.value());
        for (Dish dish: dishList) {
            save(dish);
        }
    }

    public void save(Dish dish) {
        dishRepository.save(dish);
        sendToDish(dish.getId(), dish.toString());
    }

    public void delete(Dish dish) {
        dish.setName("Удалить");
        sendToDish(dish.getId(), dish.toString());
        dishRepository.delete(dish);
    }

    public void  sendToDish(int id, String dish) {
        kafkaTemplate.send("from_admin_to_dish", id, dish);
    }

    private List<Dish> getDishesFromDish(String str) {
        List<Dish> dishList = new ArrayList<>();
        boolean  dishExist = false;
        String[] array = str.substring(1, str.length() - 1).split(", ");
        for (String element : array) {
            Dish dish = findByName(element);
            if (!"Блюдо закончилось!".equals(dish.getName()) || !"Блюдо не найдено!".equals(dish.getName())) {
                dishExist = true;
                int amount = dish.getAmount();
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
}