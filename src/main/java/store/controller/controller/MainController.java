package store.controller.controller;

import store.dto.BuyProductDTO;
import store.dto.request.AddProductRequest;
import store.dto.request.BuyProductRequest;
import store.dto.request.MinusProductRequest;
import store.dto.response.ApplyPromotionResponse;
import store.dto.response.GetProductListResponse;
import store.dto.response.GetReceiptResponse;
import store.dto.response.LackPromotionResponse;
import store.service.PosService;
import store.service.StoreService;
import store.util.StoreParser;
import store.view.InputView;
import store.view.OutputView;

import java.util.List;
import java.util.function.Supplier;

public class MainController {

    private static final int END_POINT = -1;
    private final StoreService storeService = new StoreService();
    private final PosService posService = new PosService();
    private final StoreController storeController = new StoreController(storeService);
    private final PosController posController = new PosController(posService);

    public void start() {
        do {
            play();
        } while (repeat());
    }

    private void play() {
        printProductList();
        process(this::buy);
        applyPromotion();
        lackPromotion();
        calculateProduct();
        applyMembership();
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
        List<BuyProductDTO> buyProductDTOS = StoreParser.parseBuyProductRequest(InputView.readUserInput());
        posController.buyProduct(BuyProductRequest.from(buyProductDTOS));
    }

    private void applyPromotion() {
        while (true) {
            ApplyPromotionResponse response = storeController.applyPromotion();
            if (checkLastIndex(response.index())) break;
            OutputView.printApplyBonusProduct(response.name(), response.quantity());
            addProduct(response);
        }
    }

    private boolean checkLastIndex(int index) {
        return index == END_POINT;
    }

    private void addProduct(ApplyPromotionResponse response) {
        if (process(this::inputYorN)) {
            posController.addProduct(AddProductRequest.of(response.index(), response.quantity()));
        }
    }

    private void lackPromotion() {
        while (true) {
            LackPromotionResponse response = storeController.lackPromotion();
            if (checkLastIndex(response.index())) break;
            OutputView.printLackBonusProduct(response.name(), response.quantity());
            minusProduct(response);
        }
    }

    private void minusProduct(LackPromotionResponse response) {
        if (!process(this::inputYorN)) {
            posController.minusProduct(MinusProductRequest.of(response.index(), response.quantity()));
        }
    }

    private void calculateProduct() {
        posController.calculateProduct();
    }

    private void applyMembership() {
        if (process(this::inputYorN)) {
            posController.useMembership();
        }
    }

    private void result() {
        GetReceiptResponse response = storeController.getReceipt();
        OutputView.printReceipt(response.purchaseDetails(), response.presentationProducts(), response.amounts());
    }

    private Boolean inputYorN() {
        String input = InputView.readUserInput();
        validateYorN(input);
        return input.equals("Y");
    }

    private void validateYorN(String input) {
        if (!(input.equals("Y") || input.equals("N"))) {
            throw new IllegalArgumentException("Y/N 중 하나를 입력해주세요.");
        }
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
