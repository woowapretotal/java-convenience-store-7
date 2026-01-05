package store.application.service.request;

public record OrderProductRequest(
        String name,
        int quantity
) {
}
