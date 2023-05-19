package ru.domain.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "dishes")
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    private byte[] image;

    private String name;

    private int price;

    private int amount;

    @ManyToMany(mappedBy = "dishes")
    private List<Order> orders = new ArrayList<>();

    @Override
    public String toString() {
        return  name;
    }
}