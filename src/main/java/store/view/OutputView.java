package store.view;

import java.util.Map;
import store.domain.Product;
import store.domain.Purchase;
import store.domain.Receipt;

public class OutputView {

    public void printProducts(Map<String, Product> products) {
        printStart();
        for (Product product : products.values()) {
            String price = String.format("%,d", product.getPrice());

            if (product.getPromotion() != null) {
                if (product.getPromotionQuantity() != 0) {
                    System.out.println(
                            "- " + product.getName() + " " + price + "원 " + product.getPromotionQuantity()
                                    + "개 " + product.getPromotion().getName());
                } else {
                    System.out.println(
                            "- " + product.getName() + " " + price + "원 " + "재고 없음 "
                                    + product.getPromotion().getName());
                }
            }

            if (product.getQuantity() != 0) {
                System.out.println(
                        "- " + product.getName() + " " + price + "원 " + product.getQuantity() + "개");
            } else {
                System.out.println("- " + product.getName() + " " + price + "원 " + "재고 없음");
            }
        }
        System.out.println();
    }

    private void printStart() {
        System.out.println("안녕하세요. W편의점입니다.");
        System.out.println("현재 보유하고 있는 상품입니다.");
        System.out.println();
    }

    public void printReceipt(Receipt receipt) {
        System.out.println("==============W 편의점================");
        System.out.println("상품명		수량	금액");
        for (Purchase purchase : receipt.getPurchases()) {
            System.out.println(purchase.getName() + "		" + purchase.getQuantity() + "	"
                    + purchase.getPrice() * purchase.getQuantity());

        }
        System.out.println("=============증	정===============");
        for (Purchase purchase : receipt.getPurchases()) {
            if (purchase.getPromotionQuantity() != 0) {
                System.out.println(purchase.getName() + "		" + purchase.getPromotionQuantity());
            }
        }
        System.out.println("====================================");
        System.out.format("총구매액 %d  %,d원\n", receipt.getTotalCount(), receipt.getTotalMoney());
        System.out.format("행사할인 %,d원\n", receipt.getPromotionDiscount());
        System.out.format("멤버십할인 %,d원\n", receipt.getMembershipDiscount());
        System.out.format("내실돈 %,d원\n",
                receipt.getTotalMoney() - receipt.getMembershipDiscount() - receipt.getPromotionDiscount());
        System.out.println();
    }
}
