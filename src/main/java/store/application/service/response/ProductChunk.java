package store.application.service.response;

public record ProductChunk(
        String productName,
        int quantity,
        int chunkPrice
) {
}
