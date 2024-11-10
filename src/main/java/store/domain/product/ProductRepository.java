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
        return products.get(name);
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }

    public void clear(){
        products.clear();
    }
}
