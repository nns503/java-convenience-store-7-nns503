package store.util;

import store.dto.BuyProductDTO;

import java.util.Arrays;
import java.util.List;

public class StoreParser {

    public static final String PRODUCT_PARSING_PATTERN = "^\\[|]$";
    private static final String BUYING_PRODUCT_FORMAT = "^[가-힣a-zA-Z0-9]+-\\d+$";
    private static final String PRODUCT_AND_QUANTITY_PATTERN = "-";
    private static final String DELIMITERS = ",";

    private StoreParser() {
    }

    public static List<BuyProductDTO> parseBuyProductRequest(String input) {
        List<String> buyingText = parseBuyProducts(input);
        return buyingText.stream()
                .map(text -> {
                    String[] split = text.split(PRODUCT_AND_QUANTITY_PATTERN);
                    return BuyProductDTO.of(split[0], Parser.parseStringToInt(split[1]));
                })
                .toList();
    }

    private static List<String> parseBuyProducts(String input) {
        String[] splitInput = input.split(DELIMITERS, -1);
        return Arrays.stream(splitInput)
                .map(word -> {
                    String parsingWord = word.replaceAll(PRODUCT_PARSING_PATTERN, "");
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
