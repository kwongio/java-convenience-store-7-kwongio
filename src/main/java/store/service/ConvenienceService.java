package store.service;

import java.util.List;
import java.util.Map;
import store.domain.Product;
import store.domain.Promotion;
import store.domain.Purchase;
import store.domain.Receipt;
import store.domain.ShortageQuantity;
import store.domain.request.PurchaseRequest;

public class ConvenienceService {
    private final ProductService productService;
    private final PromotionService promotionService;

    public ConvenienceService(ProductService productService, PromotionService promotionService) {
        this.productService = productService;
        this.promotionService = promotionService;
    }

    public Receipt sell(List<PurchaseRequest> purchaseRequests, String membership) {
        return calculate(purchaseRequests, membership, productService.getProducts());
    }

    private Receipt calculate(List<PurchaseRequest> purchaseRequests, String membership, Map<String, Product> products) {
        List<Purchase> purchases = purchaseRequests.stream()
                .filter(purchaseRequest -> products.containsKey(purchaseRequest.getName()))
                .map(purchaseRequest -> {
                    Product product = products.get(purchaseRequest.getName());
                    return new Purchase(
                            purchaseRequest.getName(),
                            purchaseRequest.getQuantity(),
                            product.getPrice(),
                            0);
                }).toList();
        return new Receipt(purchases, membership);
    }


    public void setPromotion(Map<String, Promotion> promotions) {
        promotionService.setPromotion(promotions);
    }

    public Map<String, Product> getProducts() {
        return productService.getProducts();
    }

    public List<ShortageQuantity> getShortageQuantityForPromotion(List<PurchaseRequest> purchases) {
        return productService.getShortageQuantityForPromotion(purchases);
    }

}
