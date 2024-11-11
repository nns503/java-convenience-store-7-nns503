package store.domain.pos;

import store.domain.product.Product;

public class PosBonusProduct {
    private String name;
    private int quantity;
    private Product product;

    public PosBonusProduct(String name, int quantity, Product product) {
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
}
