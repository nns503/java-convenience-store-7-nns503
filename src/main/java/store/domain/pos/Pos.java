package store.domain.pos;

import store.domain.product.Product;
import store.domain.promotion.Promotion;

import java.util.ArrayList;
import java.util.List;

public class Pos {

    private final static int MAX_MEMBERSHIP_DISCOUNT = 8_000;
    private final static double MEMBERSHIP_DISCOUNT_PERCENT = 0.3;
    private List<PosBuyingProduct> purchaseProducts;
    private List<PosBonusProduct> bonusProducts;
    private int allAmount;
    private int promotionAmount;
    private int promotionDiscount;
    private int membershipDiscount;
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

    public int getBonusQuantity(){
        return bonusProducts.stream()
                .mapToInt(PosBonusProduct::getBonusQuantity)
                .sum();
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
                promotionAmount += promotion.getPromotionQuantity() * buy.getProduct().getPrice() * bonus;
            }
        });
    }

    public void calculatePromotionAmount() {
        setBonusProducts();
        bonusProducts.forEach(bonus -> {
            promotionDiscount += bonus.getProduct().getPrice() * bonus.getBonusQuantity();
        });
    }

    public void updateMembershipAmount() {
        membershipDiscount = (int) Math.min((allAmount - promotionAmount) * MEMBERSHIP_DISCOUNT_PERCENT, MAX_MEMBERSHIP_DISCOUNT);
    }

    public void calculateResult() {
        resultAmount = allAmount - promotionDiscount - membershipDiscount;
    }

    public void sellProduct() {
        purchaseProducts.forEach(buy -> {
            Product product = buy.getProduct();
            product.reduceQuantity(buy.getQuantity());
        });
    }
}
