package store.common.utils;

import java.util.function.Consumer;
import java.util.function.Supplier;

public final class RetryHandler {

    private RetryHandler() {
    }

    public static <T> T execute(Supplier<T> supplier, Consumer<String> onError) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                onError.accept(e.getMessage());
            }
        }
    }
}
