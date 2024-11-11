package store.service;

import store.domain.pos.Pos;
import store.domain.pos.PosContext;
import store.domain.pos.PosPurchaseProduct;
import store.domain.product.Product;
import store.domain.product.ProductRepository;
import store.dto.BuyingProductDTO;
import store.dto.request.BuyingProductRequest;
import store.dto.response.GetProductListResponse;
import store.dto.response.GetReceiptResponse;

import java.util.ArrayList;
import java.util.List;

public class StoreService {

    private final ProductRepository productRepository = ProductRepository.INSTANCE;
    private final PosContext posContext = PosContext.INSTANCE;

    public GetProductListResponse getProductList(){
        return GetProductListResponse.from(productRepository.getAllProducts());
    }

    public void buyProduct(BuyingProductRequest request) {
        List<BuyingProductDTO> buyingProducts = request.buyingProductDTOs();
        List<PosPurchaseProduct> posPurchaseProducts = new ArrayList<>();
        buyingProducts.forEach(buy -> {
            Product findProduct = productRepository.getProductByName(buy.name());
            findProduct.checkBuyingQuantity(buy.quantity());
            posPurchaseProducts.add(new PosPurchaseProduct(buy.name(), buy.quantity(), findProduct));
        });
        posContext.init(new Pos(posPurchaseProducts));
    }

    public GetReceiptResponse getReceipt() {
        Pos pos = posContext.getPos();
        pos.calculateResult();
        return GetReceiptResponse.from(pos);
    }
}
