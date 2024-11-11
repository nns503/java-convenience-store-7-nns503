package store.service;

import store.domain.pos.Pos;
import store.domain.pos.PosContext;
import store.domain.pos.PosPurchaseData;
import store.domain.product.Product;
import store.domain.product.ProductRepository;
import store.dto.response.ApplyPromotionResponse;
import store.dto.response.GetProductListResponse;
import store.dto.response.GetReceiptResponse;
import store.dto.response.LackPromotionResponse;

public class StoreService {

    private static final int END_POINT = -1;
    private final ProductRepository productRepository = ProductRepository.INSTANCE;
    private final PosContext posContext = PosContext.INSTANCE;

    public GetProductListResponse getProductList() {
        return GetProductListResponse.from(productRepository.getAllProducts());
    }

    public ApplyPromotionResponse applyPromotion() {
        Pos pos = posContext.getPos();
        while (pos.getApplyStockIndex() != END_POINT) {
            int index = pos.getApplyStockIndex();
            pos.moveApplyStockIndex();
            PosPurchaseData purchaseData = pos.getPurchaseData(index);
            if (!purchaseData.getProduct().isPromotion()) continue;
            if (validateApplyPromotionStock(purchaseData)) continue;
            return ApplyPromotionResponse.of(index, purchaseData.getName(), 1);
        }
        return ApplyPromotionResponse.of(pos.getApplyStockIndex(), null, 0);
    }

    private boolean validateApplyPromotionStock(PosPurchaseData purchaseData) {
        int bonusQuantity = purchaseData.getProduct().getPromotion().bonusQuantity();
        int buyQuantity = purchaseData.getProduct().getPromotion().buyQuantity();
        int promotionQuantity = purchaseData.getProduct().getPromotionQuantity();
        int purchaseQuantity = purchaseData.getQuantity();
        return promotionQuantity <= purchaseQuantity || (purchaseQuantity % (buyQuantity + bonusQuantity)) != purchaseQuantity;
    }

    public LackPromotionResponse lackPromotion() {
        Pos pos = posContext.getPos();
        while (pos.getLackStockIndex() != END_POINT) {
            int index = pos.getLackStockIndex();
            pos.moveLackStockIndex();
            PosPurchaseData purchaseData = pos.getPurchaseData(index);
            if (!purchaseData.getProduct().isPromotion()) continue;
            if (getLackStock(purchaseData) > 0) continue;
            return LackPromotionResponse.of(index, purchaseData.getName(), getLackStock(purchaseData));
        }
        return LackPromotionResponse.of(pos.getApplyStockIndex(), null, 0);
    }

    private int getLackStock(PosPurchaseData purchaseData) {
        Product purchaseProduct = purchaseData.getProduct();
        int remainingQuantity = purchaseData.getQuantity() % purchaseProduct.getPromotion().getPromotionBundle();
        int excessQuantity = Math.max(0, purchaseData.getQuantity() - purchaseProduct.getPromotionQuantity());
        return remainingQuantity + excessQuantity;
    }

    public GetReceiptResponse getReceipt() {
        Pos pos = posContext.getPos();
        pos.calculateResult();
        return GetReceiptResponse.from(pos);
    }
}
