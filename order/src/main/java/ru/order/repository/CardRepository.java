package ru.order.repository;

import org.springframework.data.repository.CrudRepository;
import ru.domain.model.Card;

public interface CardRepository extends CrudRepository<Card, Integer> {
}
