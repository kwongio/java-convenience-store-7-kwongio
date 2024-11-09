package store.repository;

import java.util.Map;
import store.domain.Product;

public class ProductRepository {
    private final Map<String, Product> products;

    public ProductRepository(Map<String, Product> products) {
        this.products = products;
    }

    public Product findProduct(String name) {
        return products.get(name);
    }

    public Map<String, Product> getProducts() {
        return products;
    }
}
