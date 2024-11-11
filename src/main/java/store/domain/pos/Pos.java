package store.domain.pos;

import store.domain.promotion.Promotion;

import java.util.ArrayList;
import java.util.List;

public class Pos {

    private final static int MAX_MEMBERSHIP_DISCOUNT = 8_000;
    private final static double MEMBERSHIP_DISCOUNT_PERCENT = 0.3;

    private final List<PosPurchaseData> purchaseData;
    private final List<PosBonusData> bonusData = new ArrayList<>();
    private int allAmount;
    private int promotionAmount;
    private int promotionDiscount;
    private int membershipDiscount;
    private int resultAmount;
    private int applyStockIndex;
    private int lackStockIndex;

    public Pos(List<PosPurchaseData> purchaseData) {
        this.purchaseData = purchaseData;
    }

    public int getLackStockIndex() {
        return lackStockIndex;
    }

    public int getApplyStockIndex() {
        return applyStockIndex;
    }

    public int getAllAmount() {
        return allAmount;
    }

    public int getPromotionDiscount() {
        return promotionDiscount;
    }

    public int getMembershipDiscount() {
        return membershipDiscount;
    }

    public int getResultAmount() {
        return resultAmount;
    }

    public List<PosPurchaseData> getAllBuyingProduct() {
        return purchaseData;
    }

    public List<PosBonusData> getAllBonusData() {
        return bonusData;
    }

    public PosPurchaseData getPurchaseData(int index) {
        return purchaseData.get(index);
    }

    public int getPurchaseQuantity() {
        return purchaseData.stream()
                .mapToInt(PosPurchaseData::getQuantity)
                .sum();
    }

    public void moveApplyStockIndex() {
        applyStockIndex++;
        if (applyStockIndex == purchaseData.size()) {
            applyStockIndex = -1;
        }
    }

    public void moveLackStockIndex() {
        lackStockIndex++;
        if (lackStockIndex == purchaseData.size()) {
            lackStockIndex = -1;
        }
    }

    public void calculateAllAmount() {
        purchaseData.forEach(buy -> allAmount += buy.getProduct().getPrice() * buy.getQuantity());
    }

    private void setBonusProducts() {
        purchaseData.forEach(buy -> {
            if (!buy.getProduct().isPromotion()) return;
            Promotion promotion = buy.getProduct().getPromotion();
            int bonus = Math.min(buy.getQuantity() / (promotion.buyQuantity() + promotion.bonusQuantity()),
                    buy.getProduct().getPromotionQuantity() / (promotion.buyQuantity() + promotion.bonusQuantity()));
            bonusData.add(new PosBonusData(buy.getName(), bonus, buy.getProduct()));
            promotionAmount += promotion.getPromotionBundle() * buy.getProduct().getPrice() * bonus;
        });
    }

    public void calculatePromotionAmount() {
        setBonusProducts();
        bonusData.forEach(bonus -> promotionDiscount += bonus.getProduct().getPrice() * bonus.getBonusQuantity());
    }

    public void updateMembershipAmount() {
        membershipDiscount = (int) Math.min((allAmount - promotionAmount) * MEMBERSHIP_DISCOUNT_PERCENT, MAX_MEMBERSHIP_DISCOUNT);
    }

    public void calculateResult() {
        resultAmount = allAmount - promotionDiscount - membershipDiscount;
    }

    public void sellProduct() {
        purchaseData.forEach(buy ->
                buy.getProduct().reduceQuantity(buy.getQuantity())
        );
    }
}
