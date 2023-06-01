package ru.admin.repository;

import org.springframework.data.repository.CrudRepository;
import ru.domain.model.Authority;

public interface AuthorityRepository extends CrudRepository<Authority, Integer> {

    Authority findByAuthority(String authority);
}