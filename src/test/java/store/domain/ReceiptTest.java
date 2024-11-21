package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class ReceiptTest {

    @Test
    void calculatesTotalMoneyCorrectly() {
        Purchase purchase1 = new Purchase("콜라", 1000, 2, 1);
        Purchase purchase2 = new Purchase("사이다", 1500, 3, 0);
        Receipt receipt = new Receipt(List.of(purchase1, purchase2), Agree.NO.getValue());

        assertThat(receipt.getTotalMoney()).isEqualTo(6500);
    }


    @Test
    void noMembershipDiscountForNonMembers() {
        Purchase purchase1 = new Purchase("콜라", 1000, 2, 1);
        Purchase purchase2 = new Purchase("사이다", 1500, 3, 0);
        Receipt receipt = new Receipt(List.of(purchase1, purchase2), Agree.NO.getValue());

        assertThat(receipt.getMembershipDiscount()).isEqualTo(0);
    }

}