package store.dto;

public record BuyProductDTO(
        String name,
        int quantity
) {
    public static BuyProductDTO of(String name, int quantity) {
        return new BuyProductDTO(name, quantity);
    }
}
