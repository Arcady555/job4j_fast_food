package service;

import model.Dish;

import java.util.List;

public interface DishService {
    List<String> product();

    void cookDish(Dish dish);

    void checkDish(Dish dish);

    boolean testDish(Dish dish);

    int dishAmount();
}
