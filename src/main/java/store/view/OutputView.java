package store.view;

import java.util.Map;
import store.domain.Product;
import store.domain.Purchase;
import store.domain.Receipt;

public class OutputView {

    public void printProducts(Map<String, Product> products) {
        printStart();
        for (Product product : products.values()) {
            if(product.existsPromotion()) {
                System.out.println("- " + getName(product) + " " + getPrice(product) + " " + getPromotionQuantity(product) + " " + getPromotion(product));
            }
            System.out.println("- " + getName(product) + " " + getPrice(product) + " " + getQuantity(product));
        }
        System.out.println();
    }

    private static String getName(Product product) {
        return product.getName();
    }

    private static String getPromotion(Product product) {
        return product.getPromotion() == null ? "" : product.getPromotion().getName();
    }

    private static String getPromotionQuantity(Product product) {
        return product.getPromotionQuantity() == 0 ? "재고 없음" : (product.getPromotionQuantity() + "개");
    }

    private static String getQuantity(Product product) {
        return product.getQuantity() == 0 ? "재고 없음" : (product.getQuantity() + "개");
    }

    private static String getPrice(Product product) {
        return String.format("%,d원", product.getPrice());
    }

    private void printStart() {
        System.out.println("안녕하세요. W편의점입니다.");
        System.out.println("현재 보유하고 있는 상품입니다.");
        System.out.println();
    }

    public void printReceipt(Receipt receipt) {
        System.out.println("==============W 편의점================");
        printProduct(receipt);
        printPromotion(receipt);
        printResult(receipt);
    }

    private static void printProduct(Receipt receipt) {
        System.out.println("상품명		수량	금액");
        StringBuilder product = new StringBuilder();
        for (Purchase purchase : receipt.getPurchases()) {
            product.append(purchase.getName())
                    .append("     ")
                    .append(purchase.getQuantity())
                    .append("     ")
                    .append(purchase.getPrice() * purchase.getQuantity())
                    .append("\n");
        }
        System.out.println(product);
    }

    private static void printPromotion(Receipt receipt) {
        System.out.println("=============증	정===============");
        for (Purchase purchase : receipt.getPurchases()) {
            if (purchase.getPromotionQuantity() != 0) {
                System.out.println(purchase.getName() + "		" + purchase.getPromotionQuantity());
            }
        }
    }

    private static void printResult(Receipt receipt) {
        System.out.println("====================================");
        System.out.format("총구매액 %d  %,d원\n", receipt.getTotalCount(), receipt.getTotalMoney());
        System.out.format("행사할인 %,d원\n", receipt.getPromotionDiscount());
        System.out.format("멤버십할인 %,d원\n", receipt.getMembershipDiscount());
        System.out.format("내실돈 %,d원\n",
                receipt.getTotalMoney() - receipt.getMembershipDiscount() - receipt.getPromotionDiscount());
        System.out.println();
    }
}
