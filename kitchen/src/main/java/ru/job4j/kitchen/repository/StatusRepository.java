package ru.job4j.kitchen.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.kitchen.model.Status;

import java.util.Optional;

public interface StatusRepository extends CrudRepository<Status, Integer> {
    Optional<Status> findByName(String name);
}