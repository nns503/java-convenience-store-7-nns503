package store.domain.promotion;

import camp.nextstep.edu.missionutils.DateTimes;

import java.time.LocalDate;

public record Promotion(String name, int buyQuantity, int bonusQuantity, LocalDate startDate, LocalDate endDate) {

    public int getPromotionBundle() {
        return buyQuantity + bonusQuantity;
    }

    public boolean isPromotionPeriod() {
        LocalDate now = DateTimes.now().toLocalDate();
        return !(now.isBefore(startDate) || now.isAfter(endDate));
    }
}
