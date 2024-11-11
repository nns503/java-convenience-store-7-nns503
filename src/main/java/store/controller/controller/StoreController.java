package store.controller.controller;

import store.dto.request.BuyingProductRequest;
import store.dto.response.GetProductListResponse;
import store.dto.response.GetReceiptResponse;
import store.service.StoreService;

public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    public GetProductListResponse getProductList() {
        return storeService.getProductList();
    }

    public void buyProduct(BuyingProductRequest request) {
        storeService.buyProduct(request);
    }

    public GetReceiptResponse getReceipt() {
        return storeService.getReceipt();
    }
}
