package store.domain.product;

import camp.nextstep.edu.missionutils.DateTimes;
import store.domain.promotion.Promotion;

public class Product {

    private final String name;
    private final int price;
    private int quantity;
    private Promotion promotion;
    private int promotionQuantity;

    public Product(String name, int price, int quantity, Promotion promotion) {
        this.name = name;
        this.price = price;
        if (promotion == null) {
            this.quantity = quantity;
            return;
        }
        this.promotionQuantity = quantity;
        this.promotion = promotion;
    }

    public Product(String name, int price, int quantity, Promotion promotion, int promotionQuantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
        this.promotionQuantity = promotionQuantity;
    }

    public String getName() {
        return this.name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public int getPromotionQuantity() {
        return promotionQuantity;
    }

    public void update(Product updatingProduct) {
        if (validateNullPromotion(updatingProduct.getPromotion())) {
            promotion = updatingProduct.getPromotion();
        }
        quantity += updatingProduct.getQuantity();
        promotionQuantity += updatingProduct.getPromotionQuantity();
    }

    public void checkPurchaseQuantity(int buyingQuantity) {
        if (buyingQuantity > quantity + promotionQuantity) {
            throw new IllegalArgumentException("재고 수량을 초과하여 구매할 수 없습니다.");
        }
    }

    public boolean isPromotion() {
        return validateNullPromotion(promotion) && promotion.isPromotionPeriod(DateTimes.now().toLocalDate());
    }

    public void reduceQuantity(int reduceQuantity) {
        checkPurchaseQuantity(reduceQuantity);
        if (validateNullPromotion(promotion)) {
            reduceQuantity = reducePromotionQuantity(reduceQuantity);
        }
        quantity -= reduceQuantity;
    }

    private int reducePromotionQuantity(int reduceQuantity) {
        int reducedQuantity = Math.min(promotionQuantity, reduceQuantity);
        promotionQuantity -= reducedQuantity;
        return reduceQuantity - reducedQuantity;
    }

    private boolean validateNullPromotion(Promotion promotion) {
        return promotion != null;
    }
}
