package store.dto.request;

public record MinusProductRequest(
        int index,
        int quantity
) {
    public static MinusProductRequest of(int index,  int quantity) {
        return new MinusProductRequest(index, quantity);
    }
}
