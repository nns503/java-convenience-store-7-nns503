package store.view;

import store.dto.ProductDTO;
import store.util.Formatter;
import store.util.Parser;

import java.util.List;
import java.util.StringJoiner;

public class OutputView {

    private OutputView(){
    }

    public static void printException(String message){
        System.out.printf("%s 다시 입력해주세요.", Formatter.formatToErrorMessage(message));
    }

    public static void printProductList(List<ProductDTO> products){
        System.out.println("안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n");
        products.forEach(product ->{
            printPromotionProduct(product);
            printNormalProduct(product);
        });
    }

    private static void printPromotionProduct(ProductDTO product) {
        if(product.promotionName() != null){
            System.out.printf("- %s %s원 %s %s\n",
                    product.name(),
                    Formatter.formatToCurrency(product.price()),
                    printQuantity(product.promotionQuantity()),
                    product.promotionName());
        }
    }

    private static void printNormalProduct(ProductDTO product) {
        System.out.printf("- %s %s원 %s\n",
                product.name(),
                Formatter.formatToCurrency(product.price()),
                printQuantity(product.quantity()));
    }

    private static String printQuantity(int quantity) {
        if(quantity == 0){
            return "재고 없음 ";
        }
        return String.format("%s개", Parser.parseIntToString(quantity));
    }

    public static void printBuyProducts(){
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
    }
}
