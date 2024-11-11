package store.domain.pos;

import store.domain.promotion.Promotion;

import java.util.ArrayList;
import java.util.List;

public class Pos {
    private List<PosBuyingProduct> buyingProducts;
    private List<PosBonusProduct> bonusProducts;
    private int allAmount;
    private int promotionAmount;
    private int membershipAmount;
    private int resultAmount;
    private int applyStockIndex;
    private int lackStockIndex;


    public Pos(List<PosBuyingProduct> buyingProducts) {
        this.buyingProducts = buyingProducts;
        this.bonusProducts = new ArrayList<>();
    }

    public int getApplyStockIndex() {
        return applyStockIndex;
    }

    public void moveApplyStockIndex() {
        applyStockIndex++;
        if (applyStockIndex == buyingProducts.size()) {
            applyStockIndex = -1;
        }
    }

    public int getLackStockIndex() {
        return lackStockIndex;
    }

    public void moveLackStockIndex() {
        lackStockIndex++;
        if (lackStockIndex == buyingProducts.size()) {
            lackStockIndex = -1;
        }
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
        return buyingProducts;
    }

    public List<PosBonusProduct> getAllBonusProduct() {
        return bonusProducts;
    }

    public PosBuyingProduct getBuyingProduct(int index) {
        return buyingProducts.get(index);
    }

    public void calculateAllAmount() {
        buyingProducts.forEach(buy->{
            allAmount += buy.getProduct().getPrice() * buy.getQuantity();
        });
    }

    private void setBonusProducts(){
        buyingProducts.forEach(buy->{
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
}
