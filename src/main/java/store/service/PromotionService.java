package store.service;

import java.util.Map;
import store.domain.Promotion;
import store.repository.PromotionRepository;

public class PromotionService {

    private final PromotionRepository promotionRepository;

    public PromotionService(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    public void setPromotion(Map<String, Promotion> promotions) {
        promotionRepository.setPromotion(promotions);
    }

    public Map<String, Promotion> getPromotions() {
        return promotionRepository.getPromotions();
    }

}
