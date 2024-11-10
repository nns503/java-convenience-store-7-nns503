package store.config;

public class RepositoryConfig {

    private final ProductConfig productConfig = ProductConfig.INSTANCE;
    private final PromotionConfig promotionConfig = PromotionConfig.INSTANCE;

    public void init(){
        promotionConfig.init();
        productConfig.init();
    }
}
