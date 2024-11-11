package store.dto.request;

import store.dto.BuyProductDTO;

import java.util.List;

public record BuyProductRequest(
        List<BuyProductDTO> buyProductDTOS
) {
    public static BuyProductRequest from(List<BuyProductDTO> buyProductDTOS) {
        return new BuyProductRequest(buyProductDTOS);
    }
}
