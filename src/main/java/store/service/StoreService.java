package store.service;

import store.domain.pos.Pos;
import store.domain.pos.PosBuyingProduct;
import store.domain.pos.PosContext;
import store.domain.product.Product;
import store.domain.product.ProductRepository;
import store.dto.BuyingProductDTO;
import store.dto.request.BuyingProductRequest;
import store.dto.response.ApplyPromotionResponse;
import store.dto.response.GetProductListResponse;
import store.dto.response.GetReceiptResponse;
import store.dto.response.LackPromotionResponse;

import java.util.ArrayList;
import java.util.List;

public class StoreService {

    private final ProductRepository productRepository = ProductRepository.INSTANCE;
    private final PosContext posContext = new PosContext();

    public GetProductListResponse getProductList(){
        return GetProductListResponse.from(productRepository.getAllProducts());
    }

    public void buyProduct(BuyingProductRequest request) {
        List<BuyingProductDTO> buyingProducts = request.buyingProductDTOs();
        List<PosBuyingProduct> posBuyingProducts = new ArrayList<>();
        buyingProducts.forEach(buy -> {
            Product findProduct = productRepository.getProductByName(buy.name());
            findProduct.checkBuyingQuantity(buy.quantity());
            posBuyingProducts.add(new PosBuyingProduct(buy.name(), buy.quantity(), findProduct));
        });
        posContext.init(new Pos(posBuyingProducts));
    }
    
    public ApplyPromotionResponse applyPromotion(){
        Pos pos = posContext.getPos();
        //날짜검증추가
        while(pos.getApplyStockIndex() != -1){
            int index = pos.getApplyStockIndex();
            pos.moveApplyStockIndex();
            PosBuyingProduct buyingData = pos.getBuyingProduct(index);
            Product product = buyingData.getProduct();
            if(product.isPromotion()){
                int bonusQuantity = product.getPromotion().getBonusQuantity();
                int buyQuantity = product.getPromotion().getBuyQuantity();
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
        PosBuyingProduct updatedProduct = pos.getBuyingProduct(index);
        updatedProduct.updateQuantity(quantity);
    }
    
    public LackPromotionResponse lackPromotion(){
        Pos pos = posContext.getPos();
        while(pos.getLackStockIndex() != -1){
            int index = pos.getLackStockIndex();
            pos.moveLackStockIndex();
            PosBuyingProduct buyingData = pos.getBuyingProduct(index);
            Product product = buyingData.getProduct();
            if(product.isPromotion()){
                int bonusQuantity = product.getPromotion().getBonusQuantity();
                int buyQuantity = product.getPromotion().getBuyQuantity();
                int promotionQuantity = product.getPromotionQuantity();
                int buyingQuantity = buyingData.getQuantity();
                int remainingQuantity = buyingQuantity % (buyQuantity + bonusQuantity);
                int excessQuantity = Math.max(0, buyingQuantity - promotionQuantity);
                if(remainingQuantity + excessQuantity > 0){
                    return LackPromotionResponse.of(index, buyingData.getName(), remainingQuantity + excessQuantity);
                }
            }
        }
        return LackPromotionResponse.of(pos.getApplyStockIndex(), null, 0);
    }

    public void minusProduct(int index, int quantity){
        Pos pos = posContext.getPos();
        PosBuyingProduct updatedProduct = pos.getBuyingProduct(index);
        updatedProduct.updateQuantity(-quantity);
    }

    public void calculateProduct() {
        Pos pos = posContext.getPos();
        pos.sellProduct();
        pos.calculateAllAmount();
        pos.calculatePromotionAmount();
    }

    public void userMembership() {
        Pos pos = posContext.getPos();
        pos.updateMembershipAmount();
    }

    public GetReceiptResponse getReceipt() {
        Pos pos = posContext.getPos();
        pos.calculateResult();
        return GetReceiptResponse.from(pos);
    }
}
