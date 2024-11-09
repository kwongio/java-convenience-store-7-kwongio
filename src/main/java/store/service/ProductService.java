package store.service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
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
        return productRepository.products();
    }

    public List<ShortageQuantity> getShortageQuantityForPromotion(List<PurchaseRequest> purchases) {
        return getShortageQuantities(purchases);
    }

    private List<ShortageQuantity> getShortageQuantities(List<PurchaseRequest> purchases) {
        return purchases.stream()
                .map(purchase -> {
                    Product product = findProduct(purchase.getName());

                    if (!product.existsPromotion()) {
                        return null;
                    }

                    int quantityRequested = purchase.getQuantity();
                    int shortageQuantity = product.getShortageQuantityForPromotion(quantityRequested);

                    if (shortageQuantity <= 0) {
                        return null;
                    }

                    boolean isSufficient = product.getPromotionQuantity() >= quantityRequested + shortageQuantity;
                    int actualShortageQuantity = isSufficient
                            ? shortageQuantity
                            : quantityRequested + shortageQuantity - product.getPromotionQuantity();

                    return new ShortageQuantity(purchase, product.getName(), actualShortageQuantity, isSufficient);
                })
                .filter(Objects::nonNull)
                .toList();
    }
}
