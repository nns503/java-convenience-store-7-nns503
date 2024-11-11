package store.dto;

public record AmountDTO(
        int totalAmount,
        int totalQuantity,
        int promotionDiscount,
        int membershipDiscount,
        int resultAmount
) {
    public static AmountDTO of(int totalAmount, int totalQuantity, int promotionDiscount,  int membershipDiscount, int resultAmount) {
        return new AmountDTO(totalAmount, totalQuantity, promotionDiscount, membershipDiscount, resultAmount);
    }
}
