package store.config;

import store.domain.promotion.Promotion;
import store.domain.promotion.PromotionRepository;
import store.util.FileUtil;
import store.util.Parser;

import java.time.LocalDate;
import java.util.List;

public enum PromotionConfig {

    INSTANCE;

    private static String FILE_NAME = "promotions.md";

    private final PromotionRepository promotionRepository = PromotionRepository.INSTANCE;
    private final FileUtil fileUtil = new FileUtil();

    public void init() {
        List<String> productLines = fileUtil.readFile(FILE_NAME);
        productLines.stream()
                .skip(1)
                .forEach(line -> {
                    Promotion promotion = parsePromotion(line);
                    promotionRepository.save(promotion);
                });
    }

    private Promotion parsePromotion(String line) {
        List<String> attributes = Parser.parseDelimitersString(line);
        String name = attributes.getFirst();
        int buyQuantity = Parser.parseStringToInt(attributes.get(1));
        int bonusQuantity = Parser.parseStringToInt(attributes.get(2));
        LocalDate startDate = Parser.parseStringToLocalDate(attributes.get(3));
        LocalDate endDate = Parser.parseStringToLocalDate(attributes.get(4));
        return new Promotion(name, buyQuantity, bonusQuantity, startDate, endDate);
    }
}
