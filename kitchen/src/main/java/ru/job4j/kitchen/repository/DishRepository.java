package ru.job4j.kitchen.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.kitchen.model.Dish;

import java.util.List;
import java.util.Optional;

public interface DishRepository extends CrudRepository<Dish, Integer> {
    @Override
    List<Dish> findAll();

    Optional<Dish> findByName(String name);
}