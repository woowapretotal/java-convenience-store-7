package store.application.service.response;

import store.domain.PromotionProduct;

public record PromotionProductResponse(
        String promotionName,
        int promotionStock
) {
    public static PromotionProductResponse from(final PromotionProduct promotionProduct) {
        if (promotionProduct == null) {
            return null;
        }

        return new PromotionProductResponse(
                promotionProduct.getPromotion().getPromotionName(),
                promotionProduct.getStock());
    }
}
