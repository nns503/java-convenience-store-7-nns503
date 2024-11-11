package store.dto;

public record PurchaseDetailDTO(
        String name,
        int quantity,
        int amount
) {
    public static PurchaseDetailDTO of(String name, int quantity, int amount) {
        return new PurchaseDetailDTO(name, quantity, amount);
    }
}
