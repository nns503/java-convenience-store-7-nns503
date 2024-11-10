package store.domain.pos;

public class PosBuyingProduct {
    private String name;
    private int quantity;
    private int amount;

    public PosBuyingProduct(String name, int quantity, int amount) {
        this.name = name;
        this.quantity = quantity;
        this.amount = amount;
    }
}
