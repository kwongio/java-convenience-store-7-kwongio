package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class PromotionTest {

    @Test
    void isRangePromotion_returnsTrueWhenWithinRange() {
        Promotion promotion = new Promotion("Buy 1 Get 1", 1, 1, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31));
        LocalDate now = LocalDate.of(2023, 6, 15);

        assertThat(promotion.isRangePromotion(now)).isTrue();
    }

    @Test
    void isRangePromotion_returnsFalseWhenBeforeStartDate() {
        Promotion promotion = new Promotion("Buy 1 Get 1", 1, 1, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31));
        LocalDate now = LocalDate.of(2022, 12, 31);

        assertThat(promotion.isRangePromotion(now)).isFalse();
    }

    @Test
    void isRangePromotion_returnsFalseWhenAfterEndDate() {
        Promotion promotion = new Promotion("Buy 1 Get 1", 1, 1, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31));
        LocalDate now = LocalDate.of(2024, 1, 1);

        assertThat(promotion.isRangePromotion(now)).isFalse();
    }

    @Test
    void isRangePromotion_returnsFalseWhenOnStartDate() {
        Promotion promotion = new Promotion("Buy 1 Get 1", 1, 1, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31));
        LocalDate now = LocalDate.of(2023, 1, 1);

        assertThat(promotion.isRangePromotion(now)).isFalse();
    }

    @Test
    void isRangePromotion_returnsFalseWhenOnEndDate() {
        Promotion promotion = new Promotion("Buy 1 Get 1", 1, 1, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31));
        LocalDate now = LocalDate.of(2023, 12, 31);

        assertThat(promotion.isRangePromotion(now)).isFalse();
    }
}