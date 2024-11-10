package store.dto.response;

public record LackPromotionResponse(
        int index,
        String name,
        int quantity
) {
    public static LackPromotionResponse of(int index, String name, int quantity) {
        return new LackPromotionResponse(index, name, quantity);
    }
}
