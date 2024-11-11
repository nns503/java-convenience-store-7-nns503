package store.service;

import store.domain.pos.Pos;
import store.domain.pos.PosContext;
import store.domain.pos.PosPurchaseProduct;
import store.domain.product.Product;
import store.dto.response.ApplyPromotionResponse;
import store.dto.response.LackPromotionResponse;

public class PosService {

    private final PosContext posContext = PosContext.INSTANCE;

    public ApplyPromotionResponse applyPromotion(){
        Pos pos = posContext.getPos();
        while(pos.getApplyStockIndex() != -1){
            int index = pos.getApplyStockIndex();
            pos.moveApplyStockIndex();
            PosPurchaseProduct buyingData = pos.getPurchaseProduct(index);
            Product product = buyingData.getProduct();
            if(product.isPromotion()){
                int bonusQuantity = product.getPromotion().bonusQuantity();
                int buyQuantity = product.getPromotion().buyQuantity();
                int promotionQuantity = product.getPromotionQuantity();
                int buyingQuantity = buyingData.getQuantity();
                if(promotionQuantity > buyingQuantity && (buyingQuantity % (buyQuantity + bonusQuantity)) == buyingQuantity){
                    return ApplyPromotionResponse.of(index, buyingData.getName(), 1);
                }
            }
        }
        return ApplyPromotionResponse.of(pos.getApplyStockIndex(), null, 0);
    }

    public void addProduct(int index, int quantity) {
        Pos pos = posContext.getPos();
        PosPurchaseProduct updatedProduct = pos.getPurchaseProduct(index);
        updatedProduct.updateQuantity(quantity);
    }

    public LackPromotionResponse lackPromotion(){
        Pos pos = posContext.getPos();
        while(pos.getLackStockIndex() != -1){
            int index = pos.getLackStockIndex();
            pos.moveLackStockIndex();
            PosPurchaseProduct purchaseData = pos.getPurchaseProduct(index);
            Product purchaseProduct = purchaseData.getProduct();
            if(!purchaseProduct.isPromotion()) continue;
            int remainingQuantity = purchaseData.getQuantity() % purchaseProduct.getPromotion().getPromotionBundle();
            int excessQuantity = Math.max(0, purchaseData.getQuantity() - purchaseProduct.getPromotionQuantity());
            if(remainingQuantity + excessQuantity <= 0){
                return LackPromotionResponse.of(index, purchaseData.getName(), remainingQuantity + excessQuantity);
            }
        }
        return LackPromotionResponse.of(pos.getApplyStockIndex(), null, 0);
    }

    public void minusProduct(int index, int quantity){
        Pos pos = posContext.getPos();
        PosPurchaseProduct updatedProduct = pos.getPurchaseProduct(index);
        updatedProduct.updateQuantity(-quantity);
    }

    public void calculateProduct() {
        Pos pos = posContext.getPos();
        pos.calculateAllAmount();
        pos.calculatePromotionAmount();
        pos.sellProduct();
    }

    public void userMembership() {
        Pos pos = posContext.getPos();
        pos.updateMembershipAmount();
    }
}
