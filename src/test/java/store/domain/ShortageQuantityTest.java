package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import store.domain.request.PurchaseRequest;

class ShortageQuantityTest {

    @Test
    void getPurchaseRequestReturnsCorrectRequest() {
        PurchaseRequest request = new PurchaseRequest("콜라", 5);
        ShortageQuantity shortage = new ShortageQuantity(request, "콜라", 5, true);

        assertThat(shortage.getPurchaseRequest()).isEqualTo(request);
    }

    @Test
    void getNameReturnsCorrectName() {
        PurchaseRequest request = new PurchaseRequest("콜라", 5);
        ShortageQuantity shortage = new ShortageQuantity(request, "콜라", 5, true);

        assertThat(shortage.getName()).isEqualTo("콜라");
    }

    @Test
    void getQuantityReturnsCorrectQuantity() {
        PurchaseRequest request = new PurchaseRequest("콜라", 5);
        ShortageQuantity shortage = new ShortageQuantity(request, "콜라", 5, true);

        assertThat(shortage.getQuantity()).isEqualTo(5);
    }

    @Test
    void isPromotionReturnsTrueWhenPromotion() {
        PurchaseRequest request = new PurchaseRequest("콜라", 5);
        ShortageQuantity shortage = new ShortageQuantity(request, "콜라", 5, true);

        assertThat(shortage.isPromotion()).isTrue();
    }

    @Test
    void isPromotionReturnsFalseWhenNotPromotion() {
        PurchaseRequest request = new PurchaseRequest("콜라", 5);
        ShortageQuantity shortage = new ShortageQuantity(request, "콜라", 5, false);

        assertThat(shortage.isPromotion()).isFalse();
    }

    @Test
    void toStringReturnsCorrectFormat() {
        PurchaseRequest request = new PurchaseRequest("콜라", 5);
        ShortageQuantity shortage = new ShortageQuantity(request, "콜라", 5, true);

        assertThat(shortage.toString()).isEqualTo("ShortageQuantity{purchaseRequest=Purchase{name='콜라', quantity=5}, name='콜라', quantity=5, isPromotion=true}");
    }
}