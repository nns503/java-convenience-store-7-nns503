package store.service;

import store.domain.pos.Pos;
import store.domain.pos.PosContext;
import store.domain.pos.PosPurchaseProduct;
import store.domain.product.Product;
import store.domain.product.ProductRepository;
import store.dto.response.ApplyPromotionResponse;
import store.dto.response.GetProductListResponse;
import store.dto.response.GetReceiptResponse;
import store.dto.response.LackPromotionResponse;

public class StoreService {

    private final ProductRepository productRepository = ProductRepository.INSTANCE;
    private final PosContext posContext = PosContext.INSTANCE;

    public GetProductListResponse getProductList() {
        return GetProductListResponse.from(productRepository.getAllProducts());
    }

    public ApplyPromotionResponse applyPromotion() {
        Pos pos = posContext.getPos();
        while (pos.getApplyStockIndex() != -1) {
            int index = pos.getApplyStockIndex();
            pos.moveApplyStockIndex();
            PosPurchaseProduct buyingData = pos.getPurchaseProduct(index);
            Product product = buyingData.getProduct();
            if (product.isPromotion()) {
                int bonusQuantity = product.getPromotion().bonusQuantity();
                int buyQuantity = product.getPromotion().buyQuantity();
                int promotionQuantity = product.getPromotionQuantity();
                int buyingQuantity = buyingData.getQuantity();
                if (promotionQuantity > buyingQuantity && (buyingQuantity % (buyQuantity + bonusQuantity)) == buyingQuantity) {
                    return ApplyPromotionResponse.of(index, buyingData.getName(), 1);
                }
            }
        }
        return ApplyPromotionResponse.of(pos.getApplyStockIndex(), null, 0);
    }

    public LackPromotionResponse lackPromotion() {
        Pos pos = posContext.getPos();
        while (pos.getLackStockIndex() != -1) {
            int index = pos.getLackStockIndex();
            pos.moveLackStockIndex();
            PosPurchaseProduct purchaseData = pos.getPurchaseProduct(index);
            Product purchaseProduct = purchaseData.getProduct();
            if (!purchaseProduct.isPromotion()) continue;
            int remainingQuantity = purchaseData.getQuantity() % purchaseProduct.getPromotion().getPromotionBundle();
            int excessQuantity = Math.max(0, purchaseData.getQuantity() - purchaseProduct.getPromotionQuantity());
            if (remainingQuantity + excessQuantity <= 0) {
                return LackPromotionResponse.of(index, purchaseData.getName(), remainingQuantity + excessQuantity);
            }
        }
        return LackPromotionResponse.of(pos.getApplyStockIndex(), null, 0);
    }

    public GetReceiptResponse getReceipt() {
        Pos pos = posContext.getPos();
        pos.calculateResult();
        return GetReceiptResponse.from(pos);
    }
}
