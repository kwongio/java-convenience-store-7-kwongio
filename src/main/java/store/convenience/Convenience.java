package store.convenience;

import java.util.List;
import java.util.Map;
import store.controller.ConvenienceController;
import store.domain.Product;
import store.domain.Receipt;
import store.domain.ShortageQuantity;
import store.domain.request.PurchaseRequest;
import store.view.InputView;
import store.view.OutputView;

public class Convenience {
    private final ConvenienceController convenienceController;
    private final OutputView outputView;
    private final InputView inputView;

    public Convenience(ConvenienceController convenienceController, OutputView outputView, InputView inputView) {
        this.convenienceController = convenienceController;
        this.outputView = outputView;
        this.inputView = inputView;
    }

    public void operate() {
        while (true) {
            // 환영 인사와 함께 상품명, 가격, 프로모션 이름, 재고를 안내한다. 만약 재고가 0개라면 재고 없음을 출력한다
            Map<String, Product> products = convenienceController.getProducts();
            outputView.printProducts(products);

            //구매 입력받기 구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])
            List<PurchaseRequest> purchases = inputView.inputProductAndQuantity(products);

            //프로모션 적용이 가능한 상품에 대해 고객이 해당 수량보다 적게 가져온 경우, 그 수량만큼 추가 여부를 입력받는다.
            List<ShortageQuantity> shortageQuantityForPromotion = convenienceController.getShortageQuantityForPromotion(
                    purchases);

            for (ShortageQuantity shortageQuantity : shortageQuantityForPromotion) {
                if (shortageQuantity.isPromotion()) {
                    String promotion = inputView.getPromotion(shortageQuantity);
                    if (promotion.equals("Y")) {
                        shortageQuantity.getPurchaseRequest().add(shortageQuantity.getQuantity());
                    }

                } else {
                    String notPromotion = inputView.getNotPromotion(shortageQuantity);
                    if (notPromotion.equals("N")) {
                        // TODO CONTINUE;
                    }
                }
            }

            // 맴버십 할인
            String membership = inputView.inputMembership();
            Receipt receipt = convenienceController.sell(purchases, membership);

            outputView.printReceipt(receipt);

            // 또 구매할거냐
            if (inputView.inputRePurchase().equals("N")) {
                break;
            }
        }
    }
}
