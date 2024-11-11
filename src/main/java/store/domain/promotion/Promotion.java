package store.domain.promotion;

import camp.nextstep.edu.missionutils.DateTimes;

import java.time.LocalDate;

public class Promotion {

    private final String name;
    private final int buyQuantity;
    private final int bonusQuantity;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Promotion(String name, int buyQuantity, int bonusQuantity, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.buyQuantity = buyQuantity;
        this.bonusQuantity = bonusQuantity;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public int getBuyQuantity() {
        return buyQuantity;
    }

    public int getBonusQuantity() {
        return bonusQuantity;
    }

    public int getPromotionBundle(){
        return buyQuantity + bonusQuantity;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public boolean isPromotionPeriod(){
        LocalDate now = DateTimes.now().toLocalDate();
        return !(now.isBefore(startDate) && now.isAfter(endDate));
    }
}
