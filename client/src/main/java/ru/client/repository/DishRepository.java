package ru.client.repository;

import org.springframework.data.repository.CrudRepository;
import ru.domain.model.Dish;

import java.util.List;
import java.util.Optional;

public interface DishRepository extends CrudRepository<Dish, Integer> {
    @Override
    List<Dish> findAll();

    Optional<Dish> findByName(String name);
}