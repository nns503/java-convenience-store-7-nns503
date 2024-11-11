package store.domain.promotion;

import java.time.LocalDate;

public record Promotion(String name, int buyQuantity, int bonusQuantity, LocalDate startDate, LocalDate endDate) {

    public int getPromotionBundle() {
        return buyQuantity + bonusQuantity;
    }

    public boolean isPromotionPeriod(LocalDate date) {
        return !(date.isBefore(startDate) || date.isAfter(endDate));
    }
}
