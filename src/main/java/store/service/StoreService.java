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
            if (!buyingData.getProduct().isPromotion()) continue;
            if (validateApplyPromotionStock(buyingData.getProduct(), buyingData)) continue;
            return ApplyPromotionResponse.of(index, buyingData.getName(), 1);
        }
        return ApplyPromotionResponse.of(pos.getApplyStockIndex(), null, 0);
    }

    private boolean validateApplyPromotionStock(Product product, PosPurchaseProduct buyingData) {
        int bonusQuantity = product.getPromotion().bonusQuantity();
        int buyQuantity = product.getPromotion().buyQuantity();
        int promotionQuantity = product.getPromotionQuantity();
        int buyingQuantity = buyingData.getQuantity();
        return promotionQuantity <= buyingQuantity || (buyingQuantity % (buyQuantity + bonusQuantity)) != buyingQuantity;
    }

    public LackPromotionResponse lackPromotion() {
        Pos pos = posContext.getPos();
        while (pos.getLackStockIndex() != -1) {
            int index = pos.getLackStockIndex();
            pos.moveLackStockIndex();
            PosPurchaseProduct purchaseData = pos.getPurchaseProduct(index);
            if (!purchaseData.getProduct().isPromotion()) continue;
            if (getLackStock(purchaseData) > 0) continue;
            return LackPromotionResponse.of(index, purchaseData.getName(), getLackStock(purchaseData));
        }
        return LackPromotionResponse.of(pos.getApplyStockIndex(), null, 0);
    }

    private int getLackStock(PosPurchaseProduct purchaseData) {
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
