package store.domain.promotion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum PromotionRepository {

    INSTANCE;

    private static final Map<String, Promotion> promotions = new HashMap<>();

    public void save(Promotion promotion) {
        promotions.put(promotion.name(), promotion);
    }

    public boolean isPromotion(Promotion promotion) {
        return promotions.containsKey(promotion.name());
    }

    public Promotion getPromotionByName(String name) {
        Promotion promotion = promotions.get(name);
        return promotions.get(name);
    }

    public List<Promotion> getPromotions() {
        return new ArrayList<>(promotions.values());
    }

    public void clear() {
        promotions.clear();
    }
}
