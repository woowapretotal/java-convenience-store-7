package store.common.error;

public class InsufficientPromotionStockAskException extends PurchaseAskException {
    private final int insufficientQuantity;
    private String itemName;

    public InsufficientPromotionStockAskException(ErrorMessage errorMessage, int insufficientQuantity) {
        super(errorMessage.message());
        this.insufficientQuantity = insufficientQuantity;
    }

    public void registerItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getInsufficientQuantity() {
        return insufficientQuantity;
    }

    @Override
    public String getMessage() {
        return super.getMessage().formatted(itemName, insufficientQuantity);
    }
}
