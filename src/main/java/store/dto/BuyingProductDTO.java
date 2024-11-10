package store.dto;

import store.dto.request.BuyingProductRequest;

public record BuyingProductDTO(
        String name,
        int quantity
) {
    public static BuyingProductDTO of(String name, int quantity) {
        return new BuyingProductDTO(name, quantity);
    }
}
