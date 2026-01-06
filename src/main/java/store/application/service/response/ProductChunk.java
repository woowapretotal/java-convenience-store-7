package store.application.service.response;

import store.domain.result.PurchasedProduct;

public record ProductChunk(
        String productName,
        int quantity,
        int chunkAmount
) {

    public static ProductChunk fromOriginProductChunk(PurchasedProduct purchasedProduct) {
        return new ProductChunk(
                purchasedProduct.getProductName(),
                purchasedProduct.getRequestQuantity(),
                purchasedProduct.getOriginChunkAmount()
        );
    }

    public static ProductChunk fromGiftProductChunk(PurchasedProduct purchasedProduct) {
        return new ProductChunk(
                purchasedProduct.getProductName(),
                purchasedProduct.getGiftQuantity(),
                purchasedProduct.getGiftChunkAmount()
        );
    }
}
