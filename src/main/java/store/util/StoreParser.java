package store.util;

import store.dto.BuyingProductDTO;

import java.util.Arrays;
import java.util.List;

public class StoreParser {

    private StoreParser() {
    }

    private static final String BUYING_PRODUCT_FORMAT = "^[가-힣a-zA-Z0-9]+-\\d+$";
    private static final String PARSING_PRODUCT_AND_QUANTITY = "-";
    private static final String DELIMITERS = ",";

    public static List<BuyingProductDTO> parseBuyProductRequest(String input) {
        List<String> buyingText = parseBuyProducts(input);
        return buyingText.stream()
                .map(text -> {
                    String[] split = text.split(PARSING_PRODUCT_AND_QUANTITY);
                    return BuyingProductDTO.of(split[0], Parser.parseStringToInt(split[1]));
                })
                .toList();
    }

    private static List<String> parseBuyProducts(String input) {
        String[] splitInput = input.split(DELIMITERS, -1);
        return Arrays.stream(splitInput)
                .map(word -> {
                    String parsingWord = word.replaceAll("^\\[|]$", "");
                    validateBuyingFormat(parsingWord);
                    return parsingWord;
                })
                .toList();
    }

    private static void validateBuyingFormat(String parsingWord) {
        if (!parsingWord.matches(BUYING_PRODUCT_FORMAT)) {
            System.out.println(parsingWord);
            throw new IllegalArgumentException("구매 입력 형식에 맞지 않습니다.");
        }
    }
}
