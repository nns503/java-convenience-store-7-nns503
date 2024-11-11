package store.dto.response;

import store.domain.pos.Pos;
import store.dto.AmountDTO;
import store.dto.PresentationProductDTO;
import store.dto.PurchaseDetailDTO;

import java.util.List;

public record GetReceiptResponse(
        List<PurchaseDetailDTO> purchaseDetails,
        List<PresentationProductDTO> presentationProducts,
        AmountDTO amounts
) {
    public static GetReceiptResponse from(Pos pos){
        List<PurchaseDetailDTO> purchaseDetails = getPurchaseDetails(pos);
        List<PresentationProductDTO> presentationProducts = getPresentationProducts(pos);
        AmountDTO amountDTO = getAmounts(pos);
        return new GetReceiptResponse(purchaseDetails, presentationProducts, amountDTO);
    }

    private static AmountDTO getAmounts(Pos pos) {
        return AmountDTO.of(pos.getAllAmount(), pos.getPurchaseQuantity(), pos.getPromotionDiscount(), pos.getMembershipDiscount(), pos.getResultAmount());
    }

    private static List<PresentationProductDTO> getPresentationProducts(Pos pos) {
        return pos.getAllBonusProduct()
                .stream()
                .map(product ->
                        PresentationProductDTO.of(product.getName(), product.getBonusQuantity()))
                .toList();
    }

    private static List<PurchaseDetailDTO> getPurchaseDetails(Pos pos) {
        return pos.getAllBuyingProduct().stream()
                .map(product ->
                        PurchaseDetailDTO.of(product.getName(), product.getQuantity(), product.getPurchaseAmount()))
                .toList();
    }
}
