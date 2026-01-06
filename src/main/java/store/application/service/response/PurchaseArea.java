package store.application.service.response;

import java.util.List;

public record PurchaseArea(
        List<ProductChunk> productChunks,
        int totalOriginAmount
) {

    public static PurchaseArea from(List<ProductChunk> productChunks) {
        int totalOriginAmount = calculateTotalOriginAmount(productChunks);
        return new PurchaseArea(productChunks, totalOriginAmount);
    }

    private static int calculateTotalOriginAmount(final List<ProductChunk> productChunks) {
        return productChunks.stream()
                .map(ProductChunk::chunkAmount)
                .mapToInt(Integer::intValue)
                .sum();
    }
}
