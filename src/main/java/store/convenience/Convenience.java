package store.convenience;

import static store.domain.Agree.YES;

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

    public void operating() {
        repurchase(this::operate);
    }

    public void repurchase(Supplier<String> supplier) {
        while (supplier.get().equals(YES.getValue())) {}
    }

    private String operate() {
        Map<String, Product> products = convenienceController.getProducts();
        outputView.printProducts(products);

        List<PurchaseRequest> purchases = inputView.inputProductAndQuantity(products);

        List<ShortageQuantity> shortageQuantityForPromotion = convenienceController.getShortageQuantityForPromotion(
                purchases);

        for (ShortageQuantity shortageQuantity : shortageQuantityForPromotion) {
            addShortageQuantity(shortageQuantity);

            if (!shortageQuantity.isPromotion()) {
                String notPromotion = inputView.getNotPromotion(shortageQuantity);
                if (notPromotion.equals("N")) {
                    return "Y";
                }
            }
        }

        String membership = inputView.inputMembership();
        Receipt receipt = convenienceController.sell(purchases, membership);

        outputView.printReceipt(receipt);
        return inputView.inputRePurchase();
    }

    private void addShortageQuantity(ShortageQuantity shortageQuantity) {
        if (shortageQuantity.isPromotion()) {
            String promotion = inputView.getPromotion(shortageQuantity);
            if (promotion.equals("Y")) {
                shortageQuantity.getPurchaseRequest().add(shortageQuantity.getQuantity());
            }
        }
    }


}
