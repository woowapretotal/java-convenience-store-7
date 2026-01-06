package store.application.service.response;

import store.domain.product.NullPromotionProduct;
import store.domain.product.PromotionProduct;

public record PromotionProductResponse(
        String promotionName,
        int promotionStock
) {
    public static PromotionProductResponse from(final PromotionProduct promotionProduct) {
        if (promotionProduct instanceof NullPromotionProduct) {
            return null;
        }

        return new PromotionProductResponse(
                promotionProduct.getPromotion().getPromotionName(),
                promotionProduct.getStock());
    }
}
