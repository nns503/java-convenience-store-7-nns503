package store.dto;

public record PresentationProductDTO(
        String name,
        int quantity
) {
    public static PresentationProductDTO of(String name, int quantity) {
        return new PresentationProductDTO(name, quantity);
    }
}
