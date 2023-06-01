package ru.admin.repository;

import org.springframework.data.repository.CrudRepository;
import ru.domain.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {
}