package store.application.service;

import store.application.service.request.PurchaseProductRequest;
import store.application.service.response.*;
import store.common.error.ApplicationException;
import store.common.error.ErrorMessage;
import store.common.error.GiftQuantityAskException;
import store.common.error.InsufficientPromotionStockAskException;
import store.domain.product.Product;
import store.domain.product.ProductRepository;
import store.domain.product.PurchaseProduct;
import store.domain.result.PurchasedProduct;

import java.util.List;

public class OrderService {
    private static final Double MEMBERSHIP_DISCOUNT_RATE = 0.3;
    private static final int MAX_MEMBERSHIP_DISCOUNT_AMOUNT = 8000;

    private final ProductRepository productRepository;

    public OrderService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ReceiptResponse purchaseProducts(final List<PurchaseProductRequest> purchaseProductRequests, final PurchaseCallback purchaseCallback) {
        List<PurchaseProduct> purchaseProducts = purchaseProductRequests.stream()
                .map(purchaseProductRequest -> {
                    Product product = findProductBy(purchaseProductRequest);
                    return new PurchaseProduct(product, purchaseProductRequest.quantity());
                })
                .toList();

        List<PurchasedProduct> purchasedProducts = purchaseProducts.stream()
                .map(purchaseProduct -> this.purchaseProduct(purchaseProduct, purchaseCallback))
                .toList();

        PurchaseArea purchaseArea = toPurchaseArea(purchasedProducts);
        int noPromotionAppliedAmount = purchaseArea.totalOriginAmount() - calculateTotalPromotionAppliedAmount(purchasedProducts);
        int memberShipDiscountAmount = calculateMemberShipDiscountAmount(noPromotionAppliedAmount, purchaseCallback);

        GiftArea giftArea = toGiftArea(purchasedProducts);
        MembershipArea membershipArea = new MembershipArea(memberShipDiscountAmount);

        int totalPayAmount = purchaseArea.totalOriginAmount() - giftArea.totalGiftAmount() - memberShipDiscountAmount;
        return new ReceiptResponse(purchaseArea, giftArea, membershipArea, totalPayAmount);
    }

    private int calculateTotalPromotionAppliedAmount(final List<PurchasedProduct> purchasedProducts) {
        return purchasedProducts.stream()
                .map(PurchasedProduct::getPromotionAppliedAmount)
                .mapToInt(Integer::intValue)
                .sum();
    }

    private int calculateMemberShipDiscountAmount(final int noPromotionAppliedAmount, final PurchaseCallback purchaseCallback) {
        if (purchaseCallback.confirmMemberShip()) {
            double memberShipDiscountAmount = noPromotionAppliedAmount * MEMBERSHIP_DISCOUNT_RATE;
            if (memberShipDiscountAmount >= MAX_MEMBERSHIP_DISCOUNT_AMOUNT) {
                return MAX_MEMBERSHIP_DISCOUNT_AMOUNT;
            }
            return (int) memberShipDiscountAmount;
        }
        return 0;
    }

    private GiftArea toGiftArea(final List<PurchasedProduct> purchasedProducts) {
        List<ProductChunk> giftAreaChunkList = purchasedProducts.stream()
                .map(ProductChunk::fromGiftProductChunk)
                .toList();

        return GiftArea.from(giftAreaChunkList);
    }

    private PurchaseArea toPurchaseArea(final List<PurchasedProduct> purchasedProducts) {
        List<ProductChunk> purchaseAreaChunkList = purchasedProducts.stream()
                .map(ProductChunk::fromOriginProductChunk)
                .toList();
        return PurchaseArea.from(purchaseAreaChunkList);
    }

    private PurchasedProduct purchaseProduct(PurchaseProduct purchaseProduct, PurchaseCallback callback) {

        boolean ignorePromotionAdditionOption = false;
        boolean ignoreSufficientPromotionStockOption = false;
        try {
            return purchaseProduct.purchase(ignorePromotionAdditionOption, ignoreSufficientPromotionStockOption);
        } catch (GiftQuantityAskException e) {
            ignorePromotionAdditionOption = !callback.confirmPromotionAddition(e.getMessage());
            // callback 응답이 증정 받을 수 있는 상품을 추가한 경우(Y) option = false, 수량 업데이트 진행
            if (!ignorePromotionAdditionOption) {
                purchaseProduct = purchaseProduct.withAddFreeQuantity(e.getQuantityForFree());
                return purchaseProduct.purchase(ignorePromotionAdditionOption, ignoreSufficientPromotionStockOption);
            }

            // callback 응답이 증정 받을 수 있는 상품을 추가하지 않은 경우(N) option = true, 수량 업데이트 진행
            return purchaseProduct.purchase(ignorePromotionAdditionOption, ignoreSufficientPromotionStockOption);
        } catch (InsufficientPromotionStockAskException e) {
            ignoreSufficientPromotionStockOption = callback.confirmSomeProductDefaultPurchase(e.getMessage());
            if (!ignoreSufficientPromotionStockOption) {
                purchaseProduct = purchaseProduct.withDecreaseQuantity(e.getInsufficientQuantity());
            }
            return purchaseProduct.purchase(ignorePromotionAdditionOption, ignoreSufficientPromotionStockOption);
        }
    }

    private Product findProductBy(final PurchaseProductRequest purchaseProductRequest) {
        return productRepository.findByProductName(purchaseProductRequest.name())
                .orElseThrow(() -> new ApplicationException(ErrorMessage.NOT_EXISTS_PRODUCT));
    }
}
