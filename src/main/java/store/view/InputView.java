package store.view;

import static store.config.ErrorMessage.PRODUCT_NOT_EXIST;
import static store.config.ErrorMessage.PRODUCT_QUANTITY_NOT_ENOUGH;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.DateTimes;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import store.config.ErrorMessage;
import store.domain.Product;
import store.domain.Promotion;
import store.domain.ShortageQuantity;
import store.domain.request.PurchaseRequest;

public class InputView {

    public List<PurchaseRequest> inputProductAndQuantity(Map<String, Product> products) {
        return RetryOnInvalidInput.retryOnException(() -> getPurchaseRequests(products));
    }

    private List<PurchaseRequest> getPurchaseRequests(Map<String, Product> products) {
        // TODO 대괄호 처리해야함
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        try {
            String input = Console.readLine().replaceAll("[\\[\\]]", "");
            String[] purchaseProduct = input.split(",");
            return Arrays.stream(purchaseProduct)
                    .map(product -> product.split("-"))
                    .map(product -> convertToPurchaseRequest(product, products))
                    .toList();
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    public PurchaseRequest convertToPurchaseRequest(String[] request, Map<String, Product> products) {
        String name = request[0];
        int quantity = Integer.parseInt(request[1]);

        Product product = products.get(name);
        if (product == null) {
            throw new IllegalStateException(PRODUCT_NOT_EXIST.getMessage());
        }

        Promotion promotion = product.getPromotion();
        if (promotion != null && promotion.isRangePromotion(DateTimes.now().toLocalDate())) {
            if (product.getPromotionQuantity() + product.getQuantity() < quantity) {
                throw new IllegalStateException(PRODUCT_QUANTITY_NOT_ENOUGH.getMessage());
            }
        } else if (product.getQuantity() < quantity) {
            throw new IllegalStateException(PRODUCT_QUANTITY_NOT_ENOUGH.getMessage());
        }

        return new PurchaseRequest(name, quantity);
    }

    public String inputMembership() {
        return RetryOnInvalidInput.retryOnException(this::getMemberShip);
    }

    public String inputRePurchase() {
        return RetryOnInvalidInput.retryOnException(this::getRePurchase);
    }

    private String getMemberShip() {
        System.out.println("멤버십 할인을 받으시겠습니까? (Y/N)");
        String membership = Console.readLine();
        if (!membership.equals("Y") && !membership.equals("N")) {
            throw new IllegalArgumentException(ErrorMessage.WRONG_INPUT.getMessage());
        }
        return membership;
    }

    private String getRePurchase() {
        System.out.println("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
        String rePurchase = Console.readLine();
        if (!rePurchase.equals("Y") && !rePurchase.equals("N")) {
            throw new IllegalArgumentException(ErrorMessage.WRONG_INPUT.getMessage());
        }
        return rePurchase;
    }

    public String getNotPromotion(ShortageQuantity shortageQuantity) {
        return RetryOnInvalidInput.retryOnException(() -> inputNotPromotion(shortageQuantity));
    }

    public String inputNotPromotion(ShortageQuantity shortageQuantity) {
        System.out.println("현재 " + shortageQuantity.getName() + " " + shortageQuantity.getQuantity()
                + "개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)");
        String agree = Console.readLine();
        if (!agree.equals("Y") && !agree.equals("N")) {
            throw new IllegalArgumentException(ErrorMessage.WRONG_INPUT.getMessage());
        }
        return agree;
    }

    public String getPromotion(ShortageQuantity shortageQuantity) {
        return RetryOnInvalidInput.retryOnException(() -> inputPromotion(shortageQuantity));
    }


    public String inputPromotion(ShortageQuantity shortageQuantity) {
//        현재 {상품명}은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)
        System.out.println("현재 " + shortageQuantity.getName() + "은(는) " + shortageQuantity.getQuantity()
                + "개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)");
        String agree = Console.readLine();
        if (!agree.equals("Y") && !agree.equals("N")) {
            throw new IllegalArgumentException(ErrorMessage.WRONG_INPUT.getMessage());
        }
        return agree;
    }
}
