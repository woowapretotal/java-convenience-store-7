package store.application.controller;

import store.application.controller.adapter.ConsoleInputAdapter;
import store.application.service.OrderService;
import store.application.service.ProductService;
import store.application.service.PurchaseCallback;
import store.application.service.request.PurchaseProductRequest;
import store.application.service.response.ProductResponse;
import store.application.service.response.ReceiptResponse;
import store.application.view.ConsoleOutputView;

import java.util.List;

public class StoreController extends RetryController {
    private final ProductService productService;
    private final OrderService orderService;
    private final ConsoleInputAdapter inputAdapter;

    public StoreController(final ProductService productService, final OrderService orderService, final ConsoleInputAdapter inputAdapter, final ConsoleOutputView outputView) {
        super(outputView);
        this.productService = productService;
        this.orderService = orderService;
        this.inputAdapter = inputAdapter;
    }

    public void orderWithRetrying() {
        do {
            order();
        } while (inputAdapter.readContinuePurchaseOption());
    }

    private void order() {
        List<ProductResponse> productResponses = productService.findAll();
        outputView.printProductsInformation(productResponses);

        ReceiptResponse receiptResponses = retrying(() -> {
            List<PurchaseProductRequest> purchaseProductRequests = inputAdapter.readOrderRequest();
            PurchaseCallback purchaseCallback = makePurchaseCallback();
            return orderService.purchaseProducts(purchaseProductRequests, purchaseCallback);
        });
        outputView.printReceipt(receiptResponses);
    }

    private PurchaseCallback makePurchaseCallback() {
        return new PurchaseCallback() {
            @Override
            public boolean confirmPromotionAddition(final String errorMessage) {
                return retrying(() -> inputAdapter.readOptionFromErrorMessage(errorMessage));
            }

            @Override
            public boolean confirmSomeProductDefaultPurchase(final String errorMessage) {
                return retrying(() -> inputAdapter.readOptionFromErrorMessage(errorMessage));
            }

            @Override
            public boolean confirmMemberShip() {
                return retrying(inputAdapter::readMemberShipOption);
            }
        };
    }

    /*private ReceiptResponse makeReceipt(final List<ProductChunk> orderProductChunks) {
        PurchaseArea purchaseArea = new PurchaseArea(orderProductChunks, 13000);

        List<ProductChunk> giftProductChunk = List.of(
                new ProductChunk("콜라", 1, 1000)
        );
        GiftArea giftArea = new GiftArea(giftProductChunk, 1000);

        MembershipArea membershipArea = new MembershipArea(3000);

        return new ReceiptResponse(purchaseArea, giftArea, membershipArea, 9000);
    }*/
}
