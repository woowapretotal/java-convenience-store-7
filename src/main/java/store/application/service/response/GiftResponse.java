package store.application.service.response;

import java.util.List;

public record GiftResponse(
        List<ProductChunk> productChunks,
        int totalGiftAmount
) {
}
