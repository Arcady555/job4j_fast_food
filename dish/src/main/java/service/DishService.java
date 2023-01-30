package service;

import java.util.List;

public interface DishService {
    List<String> product();

    void cookDish();

    void checkDish();

    boolean testDish();

    int dishAmount();
}
