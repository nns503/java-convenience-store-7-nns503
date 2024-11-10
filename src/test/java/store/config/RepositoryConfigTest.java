package store.config;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.domain.product.Product;
import store.domain.product.ProductRepository;
import store.domain.promotion.Promotion;
import store.domain.promotion.PromotionRepository;
import store.fixture.ProductFixture;

import static org.assertj.core.api.Assertions.*;
import static store.fixture.ProductFixture.*;
import static store.fixture.PromotionFixture.탄산투플원;
import static store.fixture.PromotionFixture.프로모션목록;

public class RepositoryConfigTest {

    private final ProductRepository productRepository = ProductRepository.INSTANCE;
    private final PromotionRepository promotionRepository = PromotionRepository.INSTANCE;
    private static final PromotionConfig promotionConfig = PromotionConfig.INSTANCE;
    private static final ProductConfig productConfig = ProductConfig.INSTANCE;


    @BeforeEach
    void setUp() {
        productRepository.clear();
        promotionRepository.clear();
        promotionConfig.init();
        productConfig.init();
    }


    @Test
    void 초기_상품_목록() {
        assertThat(productRepository.getAllProducts().size()).isEqualTo(재고.size());
        재고
                .forEach(product -> {
                    Product findProduct = productRepository.getProductByName(product.getName());
                    assertThat(findProduct.getName()).isEqualTo(product.getName());
                    assertThat(findProduct.getPrice()).isEqualTo(product.getPrice());
                    assertThat(findProduct.getQuantity()).isEqualTo(product.getQuantity());
                    assertThat(findProduct.getPromotionQuantity()).isEqualTo(product.getPromotionQuantity());
                    if (findProduct.getPromotion() != null && product.getPromotion() != null) {
                        assertThat(findProduct.getPromotion().getName()).isEqualTo(product.getPromotion().getName());
                    }
                });
    }

    @Test
    void 초기_프로모션_목록() {
        assertThat(promotionRepository.getPromotions().size()).isEqualTo(3);
        프로모션목록
                .forEach(promotion -> {
                    Promotion findPromotion = promotionRepository.getPromotionByName(promotion.getName());
                    assertThat(findPromotion.getName()).isEqualTo(promotion.getName());
                    assertThat(findPromotion.getBuyQuantity()).isEqualTo(promotion.getBuyQuantity());
                    assertThat(findPromotion.getBonusQuantity()).isEqualTo(promotion.getBonusQuantity());
                    assertThat(findPromotion.getStartDate()).isEqualTo(promotion.getStartDate());
                    assertThat(findPromotion.getEndDate()).isEqualTo(promotion.getEndDate());
                });
    }
}
