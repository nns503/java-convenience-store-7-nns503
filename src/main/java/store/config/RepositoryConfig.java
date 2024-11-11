package store.config;

import store.domain.product.ProductRepository;
import store.domain.promotion.PromotionRepository;

public enum RepositoryConfig {

    INSTANCE;

    private final ProductConfig productConfig = ProductConfig.INSTANCE;
    private final PromotionConfig promotionConfig = PromotionConfig.INSTANCE;
    private final ProductRepository productRepository = ProductRepository.INSTANCE;
    private final PromotionRepository promotionRepository = PromotionRepository.INSTANCE;

    public void init() {
        productRepository.clear();
        promotionRepository.clear();
        promotionConfig.init();
        productConfig.init();
    }
}
