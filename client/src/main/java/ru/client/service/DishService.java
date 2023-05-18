package ru.client.service;

import ru.domain.model.Dish;

import java.util.List;

public interface DishService {
    Dish findById(int id);

    List<Dish> findAll();
}