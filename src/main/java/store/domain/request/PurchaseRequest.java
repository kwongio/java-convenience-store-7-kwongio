package store.domain.request;

public class PurchaseRequest {
    private final String name;
    private int quantity;


    public PurchaseRequest(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                '}';
    }

    public void add(int quantity) {
        this.quantity += quantity;
    }
}
