package store.dto.request;

import store.dto.BuyingProductDTO;

import java.util.List;

public record BuyingProductRequest(
        List<BuyingProductDTO> buyingProductDTOs
) {
    public static BuyingProductRequest from(List<BuyingProductDTO> buyingProductDTOs) {
        return new BuyingProductRequest(buyingProductDTOs);
    }
}
