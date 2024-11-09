package store.service;

import java.util.ArrayList;
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
        List<Purchase> purchases = new ArrayList<>();
        //PROMOTION 개수랑, 총 금액이랑 멤버쉽 할인 증정
        purchaseRequests.forEach(purchaseRequest -> {
            Product product = products.get(purchaseRequest.getName());
            int promotionCount = product.promotionApplyCount(purchaseRequest.getQuantity());
            product.sell(purchaseRequest.getQuantity());
            purchases.add(
                    new Purchase(purchaseRequest.getName(), purchaseRequest.getQuantity(), product.getPrice(),
                            promotionCount));
        });
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
