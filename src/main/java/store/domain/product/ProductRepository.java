package store.domain.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ProductRepository {

    INSTANCE;

    private static final Map<String, Product> products = new HashMap<>();

    public void save(Product product) {
        products.put(product.getName(), product);
    }

    public boolean isProduct(Product product) {
        return products.containsKey(product.getName());
    }

    public Product getProductByName(String name) {
        Product product = products.get(name);
        validateNoSuchProduct(product);
        return products.get(name);
    }

    private void validateNoSuchProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("존재하지 않는 상품입니다.");
        }
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }

    public void clear() {
        products.clear();
    }
}
