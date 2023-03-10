package ru.job4j.order.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import ru.job4j.order.model.Product;
import ru.job4j.order.repository.ProductRepository;

import java.util.Optional;

@Data
@Service
@AllArgsConstructor
public class ProductService {
    private ProductRepository products;

    public Product findById(int id) {
        Optional<Product> optionalProduct = products.findById(id);
        Product product = new Product();
        if (optionalProduct.isPresent()) {
            product = optionalProduct.get();
        } else {
            product.setName("Продукт не найден!");
        }
        return product;
    }
}
