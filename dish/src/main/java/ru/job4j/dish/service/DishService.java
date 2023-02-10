package ru.job4j.dish.service;

import lombok.Data;
import org.springframework.stereotype.Service;
import ru.job4j.dish.model.Dish;
import ru.job4j.dish.repository.DishRepository;

import java.util.List;
import java.util.Optional;

@Service
@Data
public class DishService {
    private final DishRepository dishes;

    public Boolean save(Dish dish) {
        dishes.save(dish);
        Optional<Dish> searchDish = findById(dish.getId());
        return searchDish.filter(value -> value == dish).isPresent();
    }

    public Boolean delete(Dish dish) {
        int id = dish.getId();
        dishes.delete(dish);
        Optional<Dish> searchDish = findById(id);
        return searchDish.isEmpty();
    }

    public List<Dish> findAll() {
        return dishes.findAll();
    }

    public Optional<Dish> findById(int id) {
        return dishes.findById(id);
    }
}