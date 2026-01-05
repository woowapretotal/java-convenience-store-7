package store.common.error;


public class DomainException extends IllegalArgumentException {

    public DomainException(final ErrorMessage message, final Object... args) {
        super(message.formatted(args));
    }
}

