package store.fixture;

import store.domain.promotion.Promotion;

import java.time.LocalDate;
import java.util.List;

public class PromotionFixture {

    private PromotionFixture() {
    }

    public static Promotion 탄산투플원 = new Promotion(
            "탄산2+1",
            2,
            1,
            LocalDate.of(2024, 1, 1),
            LocalDate.of(2024, 12, 31)
    );

    public static Promotion MD추천상품 = new Promotion(
            "MD추천상품",
            1,
            1,
            LocalDate.of(2024, 1, 1),
            LocalDate.of(2024, 12, 31)
    );

    public static Promotion 반짝할인 = new Promotion(
            "반짝할인",
            1,
            1,
            LocalDate.of(2024, 11, 1),
            LocalDate.of(2024, 11, 30)
    );

    public static final List<Promotion> 프로모션목록 = List.of(탄산투플원, MD추천상품, 반짝할인);
}
