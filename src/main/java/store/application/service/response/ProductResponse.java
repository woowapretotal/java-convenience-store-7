package store.application.service.response;

import store.domain.product.Product;

public record ProductResponse(
        String name,
        int price,
        int stock,
        PromotionProductResponse promotionProductResponse
) {

    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getName(),
                product.getPrice(),
                product.getStock(),
                PromotionProductResponse.from(product.getPromotionProduct())
        );
    }
}
