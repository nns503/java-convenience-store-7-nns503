package store.view;

import store.dto.AmountDTO;
import store.dto.PresentationProductDTO;
import store.dto.ProductDTO;
import store.dto.PurchaseDetailDTO;
import store.util.Parser;

import java.util.List;

import static store.util.Formatter.*;

public class OutputView {

    private OutputView() {
    }

    public static void printException(String message) {
        System.out.printf("%s 다시 입력해 주세요.\n", formatToErrorMessage(message));
    }

    public static void printProductList(List<ProductDTO> products) {
        System.out.println("안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n");
        products.forEach(product -> {
            printPromotionProduct(product);
            printNormalProduct(product);
        });
    }

    private static void printPromotionProduct(ProductDTO product) {
        if (product.promotionName() != null) {
            System.out.printf("- %s %s원 %s %s\n",
                    product.name(),
                    formatToCurrency(product.price()),
                    printQuantity(product.promotionQuantity()),
                    product.promotionName());
        }
    }

    private static void printNormalProduct(ProductDTO product) {
        System.out.printf("- %s %s원 %s\n",
                product.name(),
                formatToCurrency(product.price()),
                printQuantity(product.quantity()));
    }

    private static String printQuantity(int quantity) {
        if (quantity == 0) {
            return "재고 없음 ";
        }
        return String.format("%s개", Parser.parseIntToString(quantity));
    }

    public static void printBuyProducts() {
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
    }

    public static void printApplyBonusProduct(String name, int quantity) {
        System.out.printf("현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)\n", name, quantity);
    }

    public static void printLackBonusProduct(String name, int quantity) {
        System.out.printf("현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)\n", name, quantity);
    }

    public static void printReceipt(List<PurchaseDetailDTO> purchaseDetails, List<PresentationProductDTO> presentationProducts, AmountDTO amounts) {
        System.out.println("==============W 편의점================");
        printPurchaseDetails(purchaseDetails);
        printPresentationProducts(presentationProducts);
        printAmounts(amounts);
    }

    private static void printPurchaseDetails(List<PurchaseDetailDTO> purchaseDetails) {
        System.out.printf("%-20s%-20s%-15s\n", "상품명", "수량", "금액");
        purchaseDetails.forEach(detail ->
                System.out.printf("%-20s%20d%15s\n",
                        detail.name(),
                        detail.quantity(),
                        formatToCurrency(detail.amount()))
        );
    }

    private static void printPresentationProducts(List<PresentationProductDTO> presentationProducts) {
        System.out.println("==============증  정==================");
        presentationProducts.forEach(product ->
                System.out.printf("%-20s%20d\n",
                        product.name(),
                        product.quantity())
        );
    }

    private static void printAmounts(AmountDTO amounts) {
        System.out.println("====================================");
        System.out.printf("%-20s%20d%15s\n", "총구매액", amounts.totalQuantity(), formatToCurrency(amounts.totalAmount()));
        System.out.printf("%-20s%35s\n", "행사할인", formatToCurrency(-amounts.promotionDiscount()));
        System.out.printf("%-20s%35s\n", "멤버십할인", formatToCurrency(-amounts.membershipDiscount()));
        System.out.printf("%-20s%35s\n", "내실돈", formatToCurrency(amounts.resultAmount()));
    }

    public static void printAdditionalBuying() {
        System.out.println("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
    }
}
