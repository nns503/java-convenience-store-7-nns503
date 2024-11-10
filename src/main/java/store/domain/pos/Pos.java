package store.domain.pos;

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
    }

    public int getApplyStockIndex() {
        return applyStockIndex;
    }

    public void moveIndex() {
        applyStockIndex++;
        if (applyStockIndex == buyingProducts.size()) {
            applyStockIndex = -1;
        }
    }

    public PosBuyingProduct getBuyingProduct(int index) {
        return buyingProducts.get(index);
    }
}
