package store.domain.request;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PurchaseRequestTest {

    @Test
    void getNameReturnsCorrectName() {
        PurchaseRequest request = new PurchaseRequest("콜라", 5);

        assertThat(request.getName()).isEqualTo("콜라");
    }

    @Test
    void getQuantityReturnsCorrectQuantity() {
        PurchaseRequest request = new PurchaseRequest("콜라", 5);

        assertThat(request.getQuantity()).isEqualTo(5);
    }

    @Test
    void addIncreasesQuantity() {
        PurchaseRequest request = new PurchaseRequest("콜라", 5);
        request.add(3);

        assertThat(request.getQuantity()).isEqualTo(8);
    }

    @Test
    void toStringReturnsCorrectFormat() {
        PurchaseRequest request = new PurchaseRequest("콜라", 5);

        assertThat(request.toString()).isEqualTo("Purchase{name='콜라', quantity=5}");
    }
}