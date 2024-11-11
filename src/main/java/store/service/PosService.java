package store.service;

import store.domain.pos.Pos;
import store.domain.pos.PosContext;
import store.domain.pos.PosPurchaseData;
import store.domain.product.Product;
import store.domain.product.ProductRepository;
import store.dto.BuyProductDTO;
import store.dto.request.BuyProductRequest;

import java.util.ArrayList;
import java.util.List;

public class PosService {

    private final ProductRepository productRepository = ProductRepository.INSTANCE;
    private final PosContext posContext = PosContext.INSTANCE;

    public void buyProduct(BuyProductRequest request) {
        List<PosPurchaseData> posPurchaseData = getPosPurchaseProduct(request.buyProductDTOS());
        posContext.init(new Pos(posPurchaseData));
    }

    private List<PosPurchaseData> getPosPurchaseProduct(List<BuyProductDTO> buyingProducts) {
        List<PosPurchaseData> posPurchaseData = new ArrayList<>();
        buyingProducts.forEach(buy -> {
            Product findProduct = productRepository.getProductByName(buy.name());
            findProduct.checkPurchaseQuantity(buy.quantity());
            posPurchaseData.add(new PosPurchaseData(buy.name(), buy.quantity(), findProduct));
        });
        return posPurchaseData;
    }

    public void addProduct(int index, int quantity) {
        Pos pos = posContext.getPos();
        PosPurchaseData updatedProduct = pos.getPurchaseData(index);
        updatedProduct.updateQuantity(quantity);
    }

    public void minusProduct(int index, int quantity) {
        Pos pos = posContext.getPos();
        PosPurchaseData updatedProduct = pos.getPurchaseData(index);
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
