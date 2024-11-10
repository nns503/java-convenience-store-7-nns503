package store.dto;

import store.domain.product.Product;
import store.domain.promotion.Promotion;

public record ProductDTO(
        String name,
        int price,
        int quantity,
        String promotionName,
        int promotionQuantity
) {
    public static ProductDTO from(Product product) {
        Promotion promotion = product.getPromotion();
        return new ProductDTO(product.getName(), product.getPrice(), product.getQuantity(), getPromotionName(promotion), product.getPromotionQuantity());
    }

    private static String getPromotionName(Promotion promotion) {
        if (promotion != null) {
            return promotion.getName();
        }
        return null;
    }
}
