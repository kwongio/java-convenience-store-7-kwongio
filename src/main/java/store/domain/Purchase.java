package store.domain;

public class Purchase {
    private final String name;
    private final int quantity;
    private final int price;
    private final int promotionQuantity;

    public Purchase(String name, int quantity, int price, int promotionQuantity) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.promotionQuantity = promotionQuantity;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }

    public int getPromotionQuantity() {
        return promotionQuantity;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", promotionQuantity=" + promotionQuantity +
                '}';
    }
}
