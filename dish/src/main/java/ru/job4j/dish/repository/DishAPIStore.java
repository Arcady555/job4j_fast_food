package ru.job4j.dish.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import ru.job4j.dish.model.Dish;

import javax.annotation.PreDestroy;
import java.util.Collections;
import java.util.List;

@Repository
public class DishAPIStore {
    @Value("${api-url}")
    private String url;

    private final RestTemplate client;

    public DishAPIStore(RestTemplate client) {
        this.client = client;
    }

    public void init() { }

    public Dish add(Dish dish) {
        System.out.println(url);
        return client.postForEntity(
                url, dish, Dish.class
        ).getBody();
    }

    public boolean replace(int id, Dish dish) {
        return client.exchange(
                String.format("%s?id=%s", url, id),
                HttpMethod.PUT,
                new HttpEntity<>(dish),
                Void.class
        ).getStatusCode() != HttpStatus.NOT_FOUND;
    }

    public boolean delete(int id) {
        return client.exchange(
                String.format("%s?id=%s", url, id),
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                Void.class
        ).getStatusCode() != HttpStatus.NOT_FOUND;
    }

    public List<Dish> findAll() {
        return getList(String.format(
                "%s/getAll", url
        ));
    }

    public List<Dish> findByName(String name) {
        return getList(String.format(
                "%s/getByName?name=%s", url, name
        ));
    }

    private List<Dish> getList(String url) {
        List<Dish> body = client.exchange(
                url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Dish>>() {
                }
        ).getBody();
        return body == null ? Collections.emptyList() : body;
    }

    public Dish findById(int id) {
        return client.getForEntity(
                String.format("%s/getById?id=%s", url, id),
                Dish.class
        ).getBody();
    }

    @PreDestroy
    public void close() throws Exception {

    }
}
