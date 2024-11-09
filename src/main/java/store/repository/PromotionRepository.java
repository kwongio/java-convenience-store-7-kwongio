package store.repository;

import java.util.Map;
import store.domain.Promotion;

public class PromotionRepository {
    private final Map<String, Promotion> promotions;

    public PromotionRepository(Map<String, Promotion> promotions) {
        this.promotions = promotions;
    }

    public void setPromotion(Map<String, Promotion> promotions) {
        this.promotions.putAll(promotions);
    }

    public Map<String, Promotion> getPromotions() {
        return promotions;
    }
}
