package store.domain;

public class Product {
    private final String name;
    private final int price;
    private int quantity;
    private int promotionQuantity;
    private Promotion promotion;

    public Product(String name, int price, int promotionQuantity, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.promotionQuantity = promotionQuantity;
        this.promotion = promotion;
    }

    public Product(String name, int price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPromotionQuantity() {
        return promotionQuantity;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public boolean existsPromotion() {
        return promotion != null;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", promotionQuantity=" + promotionQuantity +
                ", promotion=" + promotion +
                '}';
    }

    public void add(int quantity) {
        this.quantity += quantity;
    }


    public int getShortageQuantityForPromotion(int purchaseQuantity) {
        if (promotionQuantity < purchaseQuantity) {
            return promotionQuantity % (promotion.getBuy() + promotion.getGet());
        }

        int shortage = purchaseQuantity % (promotion.getBuy() + promotion.getGet());
        if (shortage == 0) {
            return 0;
        }
        return (promotion.getBuy() + promotion.getGet()) - shortage;
    }
}
