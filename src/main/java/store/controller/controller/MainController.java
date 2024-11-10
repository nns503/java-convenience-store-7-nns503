package store.controller.controller;

import store.dto.BuyingProductDTO;
import store.dto.request.BuyingProductRequest;
import store.dto.response.GetProductListResponse;
import store.service.StoreService;
import store.util.StoreParser;
import store.view.InputView;
import store.view.OutputView;

import java.util.List;

public class MainController {

    private final StoreService storeService= new StoreService();
    private final StoreController storeController = new StoreController(storeService);

    public void start(){
        printProductList();
        process(this::buy);
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

}
