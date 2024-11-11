package store.dto;

public record BonusProductDTO(
        String name,
        int quantity
) {
    public static BonusProductDTO of(String name, int quantity) {
        return new BonusProductDTO(name, quantity);
    }
}
