package store.domain.pos;

import store.domain.product.Product;

public class PosBonusProduct {
    private final String name;
    private final int bonusQuantity;
    private final Product product;

    public PosBonusProduct(String name, int quantity, Product product) {
        this.name = name;
        this.bonusQuantity = quantity;
        this.product = product;
    }

    public String getName() {
        return name;
    }

    public int getBonusQuantity() {
        return bonusQuantity;
    }

    public Product getProduct() {
        return product;
    }
}
