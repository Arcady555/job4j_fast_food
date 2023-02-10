package ru.job4j.admin.service;

import ru.job4j.admin.model.Dish;

import java.util.List;
import java.util.Optional;

public interface DishService {

    public boolean save(Dish dish);

    public boolean delete(Dish dish);

    public List<Dish> findAll();

    public Optional<Dish> findById(int id);
}