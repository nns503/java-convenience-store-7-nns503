package store.dto.request;

public record AddProductRequest(
        int index,
        int quantity
) {
    public static AddProductRequest of(int index,  int quantity) {
        return new AddProductRequest(index, quantity);
    }
}
