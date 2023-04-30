package ru.job4j.dish.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@EqualsAndHashCode
public class Dish {
    @Id
    @EqualsAndHashCode.Include
    private int id;
    private String name;
    private int amount;

    @Override
    public String toString() {
        return name;
    }

    public String toStringForAdmin() {
        return name;
    }
}