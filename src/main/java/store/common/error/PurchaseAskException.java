package store.common.error;

public class PurchaseAskException extends IllegalArgumentException {

    public PurchaseAskException() {
    }

    public PurchaseAskException(String message) {
        super(message);
    }
}
