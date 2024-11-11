package store.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.domain.pos.Pos;
import store.domain.pos.PosBonusData;
import store.domain.pos.PosPurchaseData;
import store.domain.product.Product;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static store.fixture.PromotionFixture.탄산투플원;

public class PosTest {

    public PosPurchaseData 사이다8개구매;
    public PosPurchaseData 콜라20개구매;
    public Pos 사이다콜라포스;

    @BeforeEach
    void setUp() {
        Product 콜라 = new Product("콜라", 1000, 10, 탄산투플원, 10);
        Product 사이다 = new Product("사이다", 1000, 7, 탄산투플원, 8);
        사이다8개구매 = new PosPurchaseData("사이다", 8, 사이다);
        콜라20개구매 = new PosPurchaseData("콜라", 20, 콜라);
        사이다콜라포스 = new Pos(List.of(사이다8개구매, 콜라20개구매));
    }

    @Test
    public void 구매개수() {
        assertThat(사이다콜라포스.getPurchaseQuantity()).isEqualTo(28);
    }

    @Test
    public void 구매금액() {
        사이다콜라포스.calculateAllAmount();
        assertThat(사이다콜라포스.getAllAmount()).isEqualTo(28000);
    }

    @Test
    public void 프로모션_계산() {
        사이다콜라포스.calculatePromotionAmount();
        List<PosBonusData> allBonusData = 사이다콜라포스.getAllBonusData();
        assertThat(allBonusData)
                .extracting(PosBonusData::getBonusQuantity)
                .contains(2, 3);
        assertThat(사이다콜라포스.getPromotionDiscount()).isEqualTo(5000);
    }

    @Test
    public void 재고_차감() {
        List<Product> products = 사이다콜라포스.getAllBuyingProduct().stream()
                .map(PosPurchaseData::getProduct)
                .toList();

        사이다콜라포스.sellProduct();
        assertThat(products)
                .extracting(product -> (product.getPromotionQuantity() + product.getQuantity()))
                .contains(7, 0);
    }
}
