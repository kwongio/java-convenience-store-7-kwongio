package store.domain;

import java.util.List;

public class Receipt {
    private final List<Purchase> purchases;
    private final String membership;
    private final int totalMoney;
    private final int totalCount;
    private final int promotionDiscount;
    private final int membershipDiscount;

    public Receipt(List<Purchase> purchases, String membership) {
        this.purchases = purchases;
        this.membership = membership;
        this.totalMoney = totalMoney();
        this.promotionDiscount = promotionDiscount();
        this.totalCount = totalCount();
        this.membershipDiscount = membershipDisCount(membership);
    }

    private int membershipDisCount(String membership) {
        if (membership.equals(Agree.NO.getValue())) {
            return 0;
        }
        return Math.min(8000, (int) ((totalMoney - promotionDiscount) * 0.3));
    }

    private int totalCount() {
        return purchases.stream()
                .mapToInt(Purchase::getQuantity)
                .sum();
    }

    public int totalMoney() {
        return purchases.stream()
                .mapToInt(purchase -> (purchase.getQuantity()) * purchase.getPrice())
                .sum();
    }

    public int promotionDiscount() {
        return purchases.stream()
                .mapToInt(purchase -> purchase.getPromotionQuantity() * purchase.getPrice())
                .sum();
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public String getMembership() {
        return membership;
    }

    public int getTotalMoney() {
        return totalMoney;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getPromotionDiscount() {
        return promotionDiscount;
    }

    public int getMembershipDiscount() {
        return membershipDiscount;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "purchases=" + purchases +
                ", membership='" + membership + '\'' +
                ", totalMoney=" + totalMoney +
                ", totalCount=" + totalCount +
                ", promotionDiscount=" + promotionDiscount +
                ", membershipDiscount=" + membershipDiscount +
                '}';
    }
}
