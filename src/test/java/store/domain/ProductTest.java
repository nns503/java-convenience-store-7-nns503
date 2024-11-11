package store.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.domain.product.Product;
import store.domain.product.ProductRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static store.fixture.PromotionFixture.탄산투플원;

public class ProductTest {

    private static final ProductRepository productRepository = ProductRepository.INSTANCE;
    private Product 콜라;

    @BeforeEach
    void setUpl() {
        콜라 = new Product("콜라", 1000, 10, 탄산투플원, 10);
        productRepository.clear();
        productRepository.save(콜라);
    }

    @Test
    void 존재하는_상품_반환() {
        String inputName = 콜라.getName();
        Product product = productRepository.getProductByName(inputName);
        assertThat(product.getName()).isEqualTo(콜라.getName());
        assertThat(product.getPrice()).isEqualTo(콜라.getPrice());
        assertThat(product.getQuantity()).isEqualTo(콜라.getQuantity());
    }

    @Test
    void 존재하는않는_상품_예외() {
        String inputName = "존재하지않는상품";
        assertThatThrownBy(() -> productRepository.getProductByName(inputName))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 상품_구매시_프로모션_재고부터_차감() {
        String inputName = 콜라.getName();
        Product product = productRepository.getProductByName(inputName);
        product.reduceQuantity(11);
        assertThat(product.getPromotionQuantity()).isEqualTo(0);
        assertThat(product.getQuantity()).isEqualTo(9);
    }

    @Test
    void 상품_구매시_재고_차감_많으면_예외() {
        String inputName = 콜라.getName();
        Product product = productRepository.getProductByName(inputName);
        assertThatThrownBy(() -> product.reduceQuantity(21))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 프로모션이_없는_상품() {
        Product 물 = new Product("물", 500, 10, null, 0);
        productRepository.save(물);
        String inputName = 물.getName();
        Product product = productRepository.getProductByName(inputName);
        assertThat(product.isPromotion()).isFalse();
    }

    @Test
    void 프로모션이_있는_상품() {
        String inputName = 콜라.getName();
        Product product = productRepository.getProductByName(inputName);
        assertThat(product.isPromotion()).isTrue();
    }
}
