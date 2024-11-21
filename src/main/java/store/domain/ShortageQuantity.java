package store.domain;

import store.domain.request.PurchaseRequest;

public class ShortageQuantity {
    private final PurchaseRequest purchaseRequest;
    private final String name;
    private final int quantity;
    boolean isPromotion;

    public ShortageQuantity(PurchaseRequest purchaseRequest, String name, int quantity, boolean isPromotion) {
        this.purchaseRequest = purchaseRequest;
        this.name = name;
        this.quantity = quantity;
        this.isPromotion = isPromotion;
    }

    public PurchaseRequest getPurchaseRequest() {
        return purchaseRequest;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isPromotion() {
        return isPromotion;
    }

    @Override
    public String toString() {
        return "ShortageQuantity{" +
                "purchaseRequest=" + purchaseRequest +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", isPromotion=" + isPromotion +
                '}';
    }
}