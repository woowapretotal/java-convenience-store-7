package store.common.error;

public class InsufficientStockException extends IllegalStateException {
    private final int insufficientQuantity;
    private String itemName;

    public InsufficientStockException(ErrorMessage errorMessage, final int insufficientQuantity) {
        super(errorMessage.message());
        this.insufficientQuantity = insufficientQuantity;
    }

    public InsufficientStockException(ErrorMessage errorMessage, final int insufficientQuantity, final String itemName) {
        super(errorMessage.message());
        this.insufficientQuantity = insufficientQuantity;
        this.itemName = itemName;
    }

    public void registerItemName(String itemName) {
        this.itemName = itemName;
    }

    public InsufficientStockException(final int insufficientQuantity) {
        this.insufficientQuantity = insufficientQuantity;
    }

    public int getInsufficientQuantity() {
        return insufficientQuantity;
    }

    @Override
    public String getMessage() {
        return super.getMessage().formatted(itemName, insufficientQuantity);
    }
}
