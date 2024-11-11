package store.domain.pos;

import store.domain.promotion.Promotion;

import java.util.ArrayList;
import java.util.List;

public class Pos {
    private List<PosBuyingProduct> purchaseProducts;
    private List<PosBonusProduct> bonusProducts;
    private int allAmount;
    private int promotionAmount;
    private int membershipAmount;
    private int resultAmount;
    private int applyStockIndex;
    private int lackStockIndex;


    public Pos(List<PosBuyingProduct> purchaseProducts) {
        this.purchaseProducts = purchaseProducts;
        this.bonusProducts = new ArrayList<>();
    }

    public int getApplyStockIndex() {
        return applyStockIndex;
    }

    public void moveApplyStockIndex() {
        applyStockIndex++;
        if (applyStockIndex == purchaseProducts.size()) {
            applyStockIndex = -1;
        }
    }

    public int getLackStockIndex() {
        return lackStockIndex;
    }

    public void moveLackStockIndex() {
        lackStockIndex++;
        if (lackStockIndex == purchaseProducts.size()) {
            lackStockIndex = -1;
        }
    }

    public int getPurchaseQuantity(){;
       return purchaseProducts.stream()
                .mapToInt(PosBuyingProduct::getQuantity)
                .sum();
    }

    public int getAllAmount() {
        return allAmount;
    }

    public int getPromotionAmount() {
        return promotionAmount;
    }

    public int getMembershipAmount() {
        return membershipAmount;
    }

    public int getResultAmount() {
        return resultAmount;
    }

    public List<PosBuyingProduct> getAllBuyingProduct() {
        return purchaseProducts;
    }

    public List<PosBonusProduct> getAllBonusProduct() {
        return bonusProducts;
    }

    public PosBuyingProduct getBuyingProduct(int index) {
        return purchaseProducts.get(index);
    }

    public void calculateAllAmount() {
        purchaseProducts.forEach(buy->{
            allAmount += buy.getProduct().getPrice() * buy.getQuantity();
        });
    }

    private void setBonusProducts(){
        purchaseProducts.forEach(buy->{
            Promotion promotion = buy.getProduct().getPromotion();
            if(promotion != null && promotion.isPromotionPeriod()){
                int bonus = Math.min(buy.getQuantity() / (promotion.getBuyQuantity() + promotion.getBonusQuantity()),
                        buy.getProduct().getPromotionQuantity() / (promotion.getBuyQuantity() + promotion.getBonusQuantity()));
                bonusProducts.add(new PosBonusProduct(buy.getName(), bonus, buy.getProduct()));
            }
        });
    }

    public void calculatePromotionAmount() {
        setBonusProducts();
        bonusProducts.forEach(bonus -> {
            promotionAmount += bonus.getProduct().getPrice() * bonus.getQuantity();
        });
    }

    public void updateMemberShipAmount() {
        membershipAmount = Math.min((allAmount - promotionAmount) * 30 / 100, 8000);
    }

    public void calculateResult() {
        resultAmount = allAmount - promotionAmount - membershipAmount;
    }
}
