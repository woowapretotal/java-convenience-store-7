package store.application.controller.adapter;

import store.application.service.request.PurchaseProductRequest;
import store.application.view.ConsoleInputView;
import store.application.view.ConsoleOutputView;
import store.common.utils.CSVParser;

import java.util.List;

public class ConsoleInputAdapter {
    private final ConsoleOutputView outputView;
    private final ConsoleInputView inputView;

    public ConsoleInputAdapter(final ConsoleInputView inputView, final ConsoleOutputView outputView) {
        this.outputView = outputView;
        this.inputView = inputView;
    }

    public List<PurchaseProductRequest> readOrderRequest() {
        outputView.printOnboardingMessage("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        String line = inputView.readLine();
        List<String> rawOrderProduct = CSVParser.split(line);
        return rawOrderProduct.stream()
                .map(OrderProductRequestConverter::toOrderProductRequest)
                .toList();
    }

    public boolean readOptionFromErrorMessage(String errorMessage) {
        outputView.printOnboardingMessage(errorMessage);
        return inputView.readTF();
    }

    public boolean readMemberShipOption() {
        outputView.printOnboardingMessage("멤버십 할인을 받으시겠습니까? (Y/N)");
        return inputView.readTF();
    }

    public boolean readContinuePurchaseOption() {
        outputView.printOnboardingMessage("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
        return inputView.readTF();
    }
}
