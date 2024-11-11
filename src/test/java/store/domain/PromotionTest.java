package store.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.domain.promotion.Promotion;
import store.domain.promotion.PromotionRepository;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static store.fixture.PromotionFixture.탄산투플원;

public class PromotionTest {

    private final PromotionRepository promotionRepository = PromotionRepository.INSTANCE;

    @BeforeEach
    void setUp() {
        promotionRepository.clear();
        promotionRepository.save(탄산투플원);
    }

    @Test
    void 기간_내_프로모션_TRUE(){
        Promotion promotion = 탄산투플원;
        assertThat(promotion.isPromotionPeriod(LocalDate.of(2024, 11, 15))).isTrue();
    }

    @Test
    void 기간_외_프로모션_FALSE(){
        Promotion promotion = 탄산투플원;
        assertThat(promotion.isPromotionPeriod(LocalDate.of(2023, 11, 15))).isFalse();
    }

    @Test
    void null_프로모션_null_반환(){
        String inputName = "null";
        Promotion promotion = promotionRepository.getPromotionByName(inputName);
        assertThat(promotion).isNull();
    }

    @Test
    void 존재하는_프로모션_프로모션_반환(){
        String inputName = 탄산투플원.name();
        Promotion promotion = promotionRepository.getPromotionByName(inputName);
        assertThat(promotion.name()).isEqualTo(탄산투플원.name());
        assertThat(promotion.bonusQuantity()).isEqualTo(탄산투플원.bonusQuantity());
        assertThat(promotion.buyQuantity()).isEqualTo(탄산투플원.buyQuantity());
        assertThat(promotion.startDate()).isEqualTo(탄산투플원.startDate());
        assertThat(promotion.endDate()).isEqualTo(탄산투플원.endDate());
    }
    
    @Test
    void 존재하지않는_프로모션_호출_예외(){
        String inputName = "존재하지않는프로모션";
        assertThatThrownBy(()->promotionRepository.getPromotionByName(inputName))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
