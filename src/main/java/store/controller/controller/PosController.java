package store.controller.controller;

import store.dto.request.AddProductRequest;
import store.dto.request.MinusProductRequest;
import store.dto.response.ApplyPromotionResponse;
import store.dto.response.LackPromotionResponse;
import store.service.PosService;

public class PosController {

    private final PosService posService;

    public PosController(PosService posService) {
        this.posService = posService;
    }

    public ApplyPromotionResponse applyPromotion() {
        return posService.applyPromotion();
    }

    public void addProduct(AddProductRequest request) {
        posService.addProduct(request.index(), request.quantity());
    }

    public LackPromotionResponse lackPromotion() {
        return posService.lackPromotion();
    }

    public void minusProduct(MinusProductRequest request) {
        posService.minusProduct(request.index(), request.quantity());
    }

    public void calculateProduct() {
        posService.calculateProduct();
    }

    public void useMembership() {
        posService.userMembership();
    }
}
