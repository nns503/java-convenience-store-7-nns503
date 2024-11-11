package store.controller;

import store.dto.request.AddProductRequest;
import store.dto.request.BuyProductRequest;
import store.dto.request.MinusProductRequest;
import store.service.PosService;

public class PosController {

    private final PosService posService;

    public PosController(PosService posService) {
        this.posService = posService;
    }

    public void buyProduct(BuyProductRequest request) {
        posService.buyProduct(request);
    }

    public void addProduct(AddProductRequest request) {
        posService.addProduct(request.index(), request.quantity());
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
