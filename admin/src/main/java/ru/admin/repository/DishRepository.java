package ru.admin.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.domain.model.Dish;

import java.util.List;

@Repository
public interface DishRepository extends CrudRepository<Dish, Integer> {
    @Override
    List<Dish> findAll();

    @Query("FROM Dish d WHERE d.amount = 0")
    List<Dish> findAllNotEnough();
}