package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    void addIncreasesQuantity() {
        Product product = new Product("콜라", 1000, 10);
        product.add(5);

        assertThat(product.getQuantity()).isEqualTo(15);
    }

    @Test
    void existsPromotionReturnsTrueWhenPromotionExists() {
        Promotion promotion = new Promotion("Buy 1 Get 1", 1, 1, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31));
        Product product = new Product("콜라", 1000, 10, promotion);

        assertThat(product.existsPromotion()).isTrue();
    }

    @Test
    void existsPromotionReturnsFalseWhenNoPromotion() {
        Product product = new Product("콜라", 1000, 10);

        assertThat(product.existsPromotion()).isFalse();
    }

    @Test
    void getShortageQuantityForPromotionReturnsZeroWhenNoShortage() {
        Promotion promotion = new Promotion("Buy 1 Get 1", 1, 1, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31));
        Product product = new Product("콜라", 1000, 10, promotion);

        assertThat(product.getShortageQuantityForPromotion(2)).isEqualTo(0);
    }

    @Test
    void getShortageQuantityForPromotionReturnsShortageWhenNotEnoughPromotionQuantity() {
        Promotion promotion = new Promotion("Buy 1 Get 1", 1, 1, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31));
        Product product = new Product("콜라", 1000, 1, promotion);

        assertThat(product.getShortageQuantityForPromotion(2)).isEqualTo(1);
    }

    @Test
    void getShortageQuantityForPromotionReturnsShortageWhenNotEnoughPurchaseQuantity() {
        Promotion promotion = new Promotion("Buy 1 Get 1", 1, 1, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31));
        Product product = new Product("콜라", 1000, 10, promotion);

        assertThat(product.getShortageQuantityForPromotion(3)).isEqualTo(1);
    }
}