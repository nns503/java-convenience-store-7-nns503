package store.controller.controller;

import store.dto.BuyingProductDTO;
import store.dto.request.AddProductRequest;
import store.dto.request.BuyingProductRequest;
import store.dto.request.MinusProductRequest;
import store.dto.response.ApplyPromotionResponse;
import store.dto.response.GetProductListResponse;
import store.dto.response.GetReceiptResponse;
import store.dto.response.LackPromotionResponse;
import store.service.StoreService;
import store.util.StoreParser;
import store.view.InputView;
import store.view.OutputView;

import java.util.List;
import java.util.function.Supplier;

public class MainController {

    private final StoreService storeService = new StoreService();
    private final StoreController storeController = new StoreController(storeService);

    public void start() {
        do {
            play();
        } while (repeat());
    }

    private void play() {
        printProductList();
        process(this::buy);
        process(this::applyPromotion);
        process(this::lackPromotion);
        calculateProduct();
        process(this::applyMembership);
        result();
    }

    private boolean repeat() {
        OutputView.printAdditionalBuying();
        return process(this::inputYorN);
    }

    private void printProductList() {
        GetProductListResponse response = storeController.getProductList();
        OutputView.printProductList(response.productDTOs());
    }

    private void buy() {
        OutputView.printBuyProducts();
        List<BuyingProductDTO> buyingProductDTOs = StoreParser.parseBuyProductRequest(InputView.readUserInput());
        storeController.buyProduct(BuyingProductRequest.from(buyingProductDTOs));
    }

    private void applyPromotion() {
        while (true) {
            ApplyPromotionResponse response = storeController.applyPromotion();
            if (response.index() == -1) break;
            OutputView.printApplyBonusProduct(response.name(), response.quantity());
            if (process(this::inputYorN))
                storeController.addProduct(AddProductRequest.of(response.index(), response.quantity()));
        }
    }

    private void lackPromotion() {
        while (true) {
            LackPromotionResponse response = storeController.lackPromotion();
            if (response.index() == -1) break;
            OutputView.printLackBonusProduct(response.name(), response.quantity());
            if (!process(this::inputYorN))
                storeController.minusProduct(MinusProductRequest.of(response.index(), response.quantity()));
        }
    }

    private void calculateProduct() {
        storeController.calculateProduct();
    }

    private void applyMembership() {
        if (process(this::inputYorN)) storeController.useMembership();
    }

    private void result() {
        GetReceiptResponse response = storeController.getReceipt();
        OutputView.printReceipt(response.purchaseDetails(), response.presentationProducts(), response.amounts());
    }

    private Boolean inputYorN() {
        String input = InputView.readUserInput();
        if (!(input.equals("Y") || input.equals("N"))) {
            throw new IllegalArgumentException("Y/N 중 하나를 입력해주세요");
        }
        return input.equals("Y");
    }

    private void process(Runnable action) {
        while (true) {
            try {
                action.run();
                break;
            } catch (IllegalArgumentException e) {
                OutputView.printException(e.getMessage());
            }
        }
    }

    private boolean process(Supplier<Boolean> action) {
        while (true) {
            try {
                return action.get();
            } catch (IllegalArgumentException e) {
                OutputView.printException(e.getMessage());
            }
        }
    }
}
