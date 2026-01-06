package store.application.service.response;

import java.util.List;

public record GiftArea(
        List<ProductChunk> productChunks,
        int totalGiftAmount
) {

    public static GiftArea from(List<ProductChunk> productChunks) {
        return new GiftArea(productChunks, calculateTotalGiftAmount(productChunks));
    }

    private static int calculateTotalGiftAmount(final List<ProductChunk> productChunks) {
        return productChunks.stream()
                .map(ProductChunk::chunkAmount)
                .mapToInt(Integer::intValue)
                .sum();
    }
}
