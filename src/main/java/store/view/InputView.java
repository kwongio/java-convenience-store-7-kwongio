package store.view;

import static store.config.ErrorMessage.PRODUCT_NOT_EXIST;
import static store.config.ErrorMessage.PRODUCT_QUANTITY_NOT_ENOUGH;
import static store.domain.Agree.NO;
import static store.domain.Agree.YES;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.DateTimes;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
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
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        try {
            String[] purchaseProduct = extractProduct(Console.readLine());
            return Arrays.stream(purchaseProduct)
                    .map(product -> product.split("-"))
                    .map(product -> convertToPurchaseRequest(product, products))
                    .toList();
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException(e.getMessage(), e);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    private static String[] extractProduct(String input) {
        return input.replaceAll("[\\[\\]]", "").split(",");
    }

    public PurchaseRequest convertToPurchaseRequest(String[] request, Map<String, Product> products) {
        String name = request[0];
        int quantity = Integer.parseInt(request[1]);

        Product product = products.get(name);
        validateProduct(product, quantity);

        Promotion promotion = product.getPromotion();
        validatePromotion(promotion, product, quantity);

        return new PurchaseRequest(name, quantity);
    }

    private static void validatePromotion(Promotion promotion, Product product, int quantity) {
        if (existsPromotion(promotion)) {
            if (product.getPromotionQuantity() + product.getQuantity() < quantity) {
                throw new IllegalStateException(PRODUCT_QUANTITY_NOT_ENOUGH.getMessage());
            }
        }
    }

    private void validateProduct(Product product, int quantity) {
        if (isNotFound(product)) {
            throw new IllegalStateException(PRODUCT_NOT_EXIST.getMessage());
        }
        if (notEnoughProduct(product, quantity)) {
            throw new IllegalStateException(PRODUCT_QUANTITY_NOT_ENOUGH.getMessage());
        }
    }

    private static boolean notEnoughProduct(Product product, int quantity) {
        return product.getQuantity() < quantity;
    }

    private static boolean existsPromotion(Promotion promotion) {
        return promotion != null && promotion.isRangePromotion(DateTimes.now().toLocalDate());
    }

    private boolean isNotFound(Product product) {
        return product == null;
    }

    public String inputMembership() {
        return RetryOnInvalidInput.retryOnException(this::getMemberShip);
    }

    public String inputRePurchase() {
        return RetryOnInvalidInput.retryOnException(this::getRePurchase);
    }

    private String getMemberShip() {
        System.out.println("멤버십 할인을 받으시겠습니까? (Y/N)");
        return agree(Console.readLine());
    }

    private String getRePurchase() {
        System.out.println("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
        return agree(Console.readLine());
    }

    public String getNotPromotion(ShortageQuantity shortageQuantity) {
        return RetryOnInvalidInput.retryOnException(() -> inputNotPromotion(shortageQuantity));
    }

    public String inputNotPromotion(ShortageQuantity shortageQuantity) {
        System.out.println("현재 " +
                shortageQuantity.getName() +
                " " +
                shortageQuantity.getQuantity() +
                "개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)");
        return agree(Console.readLine());
    }

    private static String agree(String agree) {
        if (!agree.equals(YES.getValue()) && !agree.equals(NO.getValue())) {
            throw new IllegalArgumentException(ErrorMessage.WRONG_INPUT.getMessage());
        }
        return agree;
    }

    public String getPromotion(ShortageQuantity shortageQuantity) {
        return RetryOnInvalidInput.retryOnException(() -> inputPromotion(shortageQuantity));
    }


    private String inputPromotion(ShortageQuantity shortageQuantity) {
        System.out.println("현재 " +
                shortageQuantity.getName() +
                "은(는) " +
                shortageQuantity.getQuantity() +
                "개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)");
        return agree(Console.readLine());
    }
}
