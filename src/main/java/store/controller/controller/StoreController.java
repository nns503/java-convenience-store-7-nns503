package store.controller.controller;

import store.dto.request.AddProductRequest;
import store.dto.request.BuyingProductRequest;
import store.dto.request.MinusProductRequest;
import store.dto.response.ApplyPromotionResponse;
import store.dto.response.GetProductListResponse;
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

    public void buyProduct(BuyingProductRequest request) {
        storeService.buyProduct(request);
    }

    public ApplyPromotionResponse applyPromotion() {
        return storeService.applyPromotion();
    }

    public void addProduct(AddProductRequest request) {
        storeService.addProduct(request.index(), request.quantity());
    }

    public LackPromotionResponse lackPromotion() {
        return storeService.lackPromotion();
    }

    public void minusProduct(MinusProductRequest request) {
        storeService.minusProduct(request.index(), request.quantity());
    }

    public void calculateAmount() {
        storeService.calculateAmount();
    }

    public void useMembership() {
        storeService.userMembership();
    }
}
