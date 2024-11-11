package store.service;

import store.domain.pos.Pos;
import store.domain.pos.PosContext;
import store.domain.pos.PosPurchaseProduct;
import store.domain.product.Product;
import store.domain.product.ProductRepository;
import store.dto.BuyingProductDTO;
import store.dto.request.BuyingProductRequest;

import java.util.ArrayList;
import java.util.List;

public class PosService {

    private final ProductRepository productRepository = ProductRepository.INSTANCE;
    private final PosContext posContext = PosContext.INSTANCE;

    public void buyProduct(BuyingProductRequest request) {
        List<PosPurchaseProduct> posPurchaseProducts = getPosPurchaseProduct(request.buyingProductDTOs());
        posContext.init(new Pos(posPurchaseProducts));
    }

    private List<PosPurchaseProduct> getPosPurchaseProduct(List<BuyingProductDTO> buyingProducts) {
        List<PosPurchaseProduct> posPurchaseProducts = new ArrayList<>();
        buyingProducts.forEach(buy -> {
            Product findProduct = productRepository.getProductByName(buy.name());
            findProduct.checkPurchaseQuantity(buy.quantity());
            posPurchaseProducts.add(new PosPurchaseProduct(buy.name(), buy.quantity(), findProduct));
        });
        return posPurchaseProducts;
    }

    public void addProduct(int index, int quantity) {
        Pos pos = posContext.getPos();
        PosPurchaseProduct updatedProduct = pos.getPurchaseProduct(index);
        updatedProduct.updateQuantity(quantity);
    }

    public void minusProduct(int index, int quantity) {
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
