package store.domain;

import org.junit.jupiter.api.Test;
import store.domain.product.ProductRepository;

public class ProductTest {

    ProductRepository productRepository = ProductRepository.INSTANCE;

    @Test
    void 초기_상품_목록_설정() {

    }

    // 같은 경우, 적은 경우
    @Test
    void 상품_구매시_재고_차감(){

    }
    
    @Test
    void 상품_재고보다_작으면_예외(){

    }
}
