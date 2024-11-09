package store.controller;

import java.util.List;
import java.util.Map;
import store.domain.Product;
import store.domain.Promotion;
import store.domain.Receipt;
import store.domain.ShortageQuantity;
import store.domain.request.PurchaseRequest;
import store.service.ConvenienceService;

public class ConvenienceController {

    private final ConvenienceService convenienceService;

    public ConvenienceController(ConvenienceService convenienceService) {
        this.convenienceService = convenienceService;
    }

    public Receipt sell(List<PurchaseRequest> purchaseRequests, String membership) {
        return convenienceService.sell(purchaseRequests, membership);
    }

    public void setPromotion(Map<String, Promotion> promotions) {
        convenienceService.setPromotion(promotions);
    }

    public Map<String, Product> getProducts() {
        return convenienceService.getProducts();
    }

    public List<ShortageQuantity> getShortageQuantityForPromotion(List<PurchaseRequest> purchases) {
        return convenienceService.getShortageQuantityForPromotion(purchases);
    }


}
