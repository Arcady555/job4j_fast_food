package ru.job4j.dish.service;

import lombok.Data;
import org.springframework.stereotype.Service;
import ru.job4j.dish.model.Dish;
import ru.job4j.dish.repository.DishAPIStore;

import java.util.List;

@Service
@Data
public class DishAPIService {
    private final DishAPIStore dishStore;

    public Dish add(Dish dish) {
        return dishStore.add(dish);
    }

    public boolean replace(int id, Dish dish) {
        return dishStore.replace(id, dish);
    }

    public boolean delete(int id) {
        return dishStore.delete(id);
    }

    public List<Dish> findAll() {
        return dishStore.findAll();
    }

    public List<Dish> findByName(String name) {
        return dishStore.findByName(name);
    }

    public Dish findById(int id) {
        return dishStore.findById(id);
    }
}