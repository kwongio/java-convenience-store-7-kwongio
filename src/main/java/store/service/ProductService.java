package store.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import store.domain.Product;
import store.domain.ShortageQuantity;
import store.domain.request.PurchaseRequest;
import store.repository.ProductRepository;

public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product findProduct(String name) {
        return productRepository.findProduct(name);
    }

    public Map<String, Product> getProducts() {
        return productRepository.getProducts();
    }

    public List<ShortageQuantity> getShortageQuantityForPromotion(List<PurchaseRequest> purchases) {
        List<ShortageQuantity> shortageQuantities = new ArrayList<>();
        for (PurchaseRequest purchase : purchases) {
            Product product = findProduct(purchase.getName());
            if (product.getPromotion() == null) {
                continue;
            }
            int quantity = purchase.getQuantity();
            int shortageQuantityForPromotion = product.getShortageQuantityForPromotion(quantity);
            if (shortageQuantityForPromotion > 0) {
                if (product.getPromotionQuantity() < purchase.getQuantity() + shortageQuantityForPromotion) {
                    shortageQuantities.add(new ShortageQuantity(purchase, product.getName(),
                            purchase.getQuantity() + shortageQuantityForPromotion - product.getPromotionQuantity(), false));
                } else {
                    shortageQuantities.add(new ShortageQuantity(purchase, product.getName(), shortageQuantityForPromotion, true));
                }
            }
        }
        return shortageQuantities;
    }
}
