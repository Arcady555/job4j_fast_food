package ru.job4j.order.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.order.model.Dish;

import java.util.List;

public interface DishRepository extends CrudRepository<Dish, Integer> {
    @Override
    List<Dish> findAll();
}