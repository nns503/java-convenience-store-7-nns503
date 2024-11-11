package store.controller;

import store.dto.response.ApplyPromotionResponse;
import store.dto.response.GetProductListResponse;
import store.dto.response.GetReceiptResponse;
import store.dto.response.LackPromotionResponse;
import store.service.StoreService;

public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    public GetProductListResponse getProductList() {
        return storeService.getProductList();
    }

    public ApplyPromotionResponse applyPromotion() {
        return storeService.applyPromotion();
    }

    public LackPromotionResponse lackPromotion() {
        return storeService.lackPromotion();
    }

    public GetReceiptResponse getReceipt() {
        return storeService.getReceipt();
    }
}
