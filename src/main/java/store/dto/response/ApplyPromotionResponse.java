package store.dto.response;

public record ApplyPromotionResponse(
        int index,
        String name,
        int quantity
) {
    public static ApplyPromotionResponse of(int index, String name, int quantity) {
        return new ApplyPromotionResponse(index, name, quantity);
    }
}
