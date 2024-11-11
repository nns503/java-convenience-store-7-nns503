package store.config;

import store.domain.product.Product;
import store.domain.product.ProductRepository;
import store.domain.promotion.Promotion;
import store.domain.promotion.PromotionRepository;
import store.util.FileUtil;
import store.util.Parser;

import java.util.List;

public enum ProductConfig {

    INSTANCE;

    private static String FILE_NAME = "products.md";

    private final PromotionRepository promotionRepository = PromotionRepository.INSTANCE;
    private final ProductRepository productRepository = ProductRepository.INSTANCE;
    private final FileUtil fileUtil = new FileUtil();

    public void init() {
        List<String> productLines = fileUtil.readFile(FILE_NAME);
        productLines.stream()
                .skip(1)
                .forEach(line -> {
                    Product product = parseProduct(line);
                    updateProduct(product);
                    productRepository.save(product);
                });
    }

    private void updateProduct(Product product) {
        if (productRepository.isProduct(product)) {
            Product existingProduct = productRepository.getProductByName(product.getName());
            product.update(existingProduct);
        }
    }

    private Product parseProduct(String line) {
        List<String> attributes = Parser.parseDelimitersString(line);
        String name = attributes.getFirst();
        int price = Parser.parseStringToInt(attributes.get(1));
        int quantity = Parser.parseStringToInt(attributes.get(2));
        Promotion promotion = promotionRepository.getPromotionByName(attributes.get(3));
        return new Product(name, price, quantity, promotion);
    }
}
