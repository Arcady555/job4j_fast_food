package ru.admin.service;

import ru.domain.model.Dish;

import java.util.List;

public interface DishService {

    List<Dish> findAll();

    List<Dish> findAllNotEnough();

    Dish findById(int id);

    void save(Dish dish);

    void delete(Dish dish);
}