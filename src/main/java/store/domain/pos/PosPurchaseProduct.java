package store.domain.pos;

import store.domain.product.Product;

public class PosPurchaseProduct {

    private final String name;
    private int quantity;
    private final Product product;

    public PosPurchaseProduct(String name, int quantity, Product product) {
        this.name = name;
        this.quantity = quantity;
        this.product = product;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getPurchaseAmount() {
        return quantity * product.getPrice();
    }

    public void updateQuantity(int quantity) {
        this.quantity += quantity;
    }
}
