package store.common.error;

public class GiftQuantityAskException extends PurchaseAskException {
    private final int quantityForFree;
    private String itemName;

    public GiftQuantityAskException(ErrorMessage errorMessage, final int quantityForFree) {
        super(errorMessage.message());
        this.quantityForFree = quantityForFree;
    }

    public void registerItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantityForFree() {
        return quantityForFree;
    }

    @Override
    public String getMessage() {
        return super.getMessage().formatted(itemName, quantityForFree);
    }
}
