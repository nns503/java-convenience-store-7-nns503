package store.dto.response;

import store.domain.product.Product;
import store.dto.ProductDTO;

import java.util.List;

public record GetProductListResponse(
        List<ProductDTO> productDTOs
) {
    public static GetProductListResponse from(List<Product> products) {
        return new GetProductListResponse(products.stream()
                .map(ProductDTO::from)
                .toList());
    }
}
