package store.application.controller.adapter;

import store.application.service.request.OrderProductRequest;
import store.application.service.response.ProductResponse;
import store.application.view.ConsoleInputView;
import store.application.view.ConsoleOutputView;

import java.util.List;

public class ConsoleInputAdapter {
    private final ConsoleOutputView outputView;
    private final ConsoleInputView inputView;

    public ConsoleInputAdapter(final ConsoleInputView inputView, final ConsoleOutputView outputView) {
        this.outputView = outputView;
        this.inputView = inputView;
    }

    public List<OrderProductRequest> readOrderRequest(List<ProductResponse> productResponses) {
        outputView.printProductsInformation(productResponses);
        outputView.printOnboardingMessage("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        return null;
    }

    public String readMenuNumber() {
        /*outputView.printMenus();
        return inputView.readLine();*/
        return null;
    }
}
