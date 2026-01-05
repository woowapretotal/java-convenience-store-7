package store.application.service.response;

import java.util.List;

public record OrderResponse(
        List<ProductChunk> productChunks,
        int totalOrderAmount
) {
}
