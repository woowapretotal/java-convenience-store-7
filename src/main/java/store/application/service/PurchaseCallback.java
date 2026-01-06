package store.application.service;

public interface PurchaseCallback {

    boolean confirmPromotionAddition(String errorMessage);

    boolean confirmSomeProductDefaultPurchase(String errorMessage);

    boolean confirmMemberShip();
}
