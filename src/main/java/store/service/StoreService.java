package store.service;

import store.domain.pos.Pos;
import store.domain.pos.PosBuyingProduct;
import store.domain.pos.PosContext;
import store.domain.product.Product;
import store.domain.product.ProductRepository;
import store.dto.BuyingProductDTO;
import store.dto.request.BuyingProductRequest;
import store.dto.response.GetProductListResponse;

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
            posBuyingProducts.add(new PosBuyingProduct(buy.name(), buy.quantity(), findProduct.getPrice()));
        });
        posContext.init(new Pos(posBuyingProducts));
    }
}
