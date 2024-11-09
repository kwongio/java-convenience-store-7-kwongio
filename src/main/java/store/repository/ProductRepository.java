package store.repository;

import java.util.Map;
import store.domain.Product;

public record ProductRepository(Map<String, Product> products) {

    public Product findProduct(String name) {
        return products.get(name);
    }
}
