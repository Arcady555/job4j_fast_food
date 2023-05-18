package ru.admin.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.admin.model.Dish;

import java.util.List;
import java.util.Optional;

@Repository
public interface DishRepository extends CrudRepository<Dish, Integer> {
    @Override
    List<Dish> findAll();

    Optional<Dish> findByName(String name);
}