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

    public Promotion getPromotionByName(String name) {
        if (name.equals("null")) return null;
        Promotion promotion = promotions.get(name);
        if (promotion == null) {
            throw new IllegalArgumentException("존재하지 않는 프로모션입니다.");
        }
        return promotion;
    }

    public List<Promotion> getPromotions() {
        return new ArrayList<>(promotions.values());
    }

    public void clear() {
        promotions.clear();
    }
}
