package store.convenience;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
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

    public void init() {
        repurchase(this::operate);
    }

    public void repurchase(Supplier<String> supplier) {
        while (true) {
            String rePurchase = supplier.get();
            if (rePurchase.equals("N")) {
                break;
            }
        }
    }

    private String operate() {
        Map<String, Product> products = convenienceController.getProducts();
        outputView.printProducts(products);

        List<PurchaseRequest> purchases = inputView.inputProductAndQuantity(products);

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
                if(notPromotion.equals("N")) {
                    return "Y";
                }
            }
        }

        String membership = inputView.inputMembership();
        Receipt receipt = convenienceController.sell(purchases, membership);

        outputView.printReceipt(receipt);
        return inputView.inputRePurchase();
    }
}
