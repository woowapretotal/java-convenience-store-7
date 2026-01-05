package store.common.error;


public class ApplicationException extends IllegalArgumentException {

    public ApplicationException(final ErrorMessage message, final Object... args) {
        super(message.formatted(args));
    }
}

