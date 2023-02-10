package ru.job4j.admin.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import ru.job4j.admin.model.Dish;

import javax.annotation.PreDestroy;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@Data
public class RestApiDishService implements DishService {
    @Value("${api-url}")
    private String url;

    private final RestTemplate client;

    @Override
    public boolean save(Dish dish) {
        return client.exchange(
                String.format("%s?id=%s", url, dish),
                HttpMethod.PUT,
                new HttpEntity<>(dish),
                Void.class
        ).getStatusCode() != HttpStatus.NOT_FOUND;
    }

    @Override
    public boolean delete(Dish dish) {
        return client.exchange(
                String.format("%s?id=%s", url, dish),
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                Void.class
        ).getStatusCode() != HttpStatus.NOT_FOUND;
    }

    @Override
    public List<Dish> findAll() {
        return getList(String.format(
                "%s/getAll", url
        ));
    }

    @Override
    public Optional<Dish> findById(int id) {
        Dish dish = client.getForEntity(
                String.format("%s/getById?id=%s", url, id),
                Dish.class
        ).getBody();
        return dish != null ? Optional.of(dish) : Optional.empty();
    }

    @PreDestroy
    public void close() throws Exception {

    }

    private List<Dish> getList(String url) {
        List<Dish> body = client.exchange(
                url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Dish>>() {
                }
        ).getBody();
        return body == null ? Collections.emptyList() : body;
    }
}