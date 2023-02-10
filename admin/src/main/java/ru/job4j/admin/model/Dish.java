package ru.job4j.admin.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class Dish {
    @EqualsAndHashCode.Include
    private int id;
    private String name;
}
