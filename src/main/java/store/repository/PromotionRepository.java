package store.repository;

import java.util.Map;
import store.domain.Promotion;

public record PromotionRepository(Map<String, Promotion> promotions) {

    public void setPromotion(Map<String, Promotion> promotions) {
        this.promotions.putAll(promotions);
    }
}
