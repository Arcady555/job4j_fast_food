package ru.job4j.dish.service;

import ru.job4j.dish.model.Dish;

import java.util.List;

public interface DishService {
    List<String> product();

    void cookDish(Dish dish);

    void checkDish(Dish dish);

    boolean testDish(Dish dish);

    int dishAmount();
}
