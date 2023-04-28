package ru.job4j.admin.service;

import lombok.Data;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.job4j.admin.model.Dish;
import ru.job4j.admin.repository.DishRepository;

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

    public void msgFromDish(ConsumerRecord<Integer, Integer> record) {
        Dish dish = findById(record.key());
        int amount = record.value();
        dish.setAmount(amount);
        save(dish);
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
}