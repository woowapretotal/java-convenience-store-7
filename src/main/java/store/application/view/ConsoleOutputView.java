package store.application.view;

import store.application.service.response.ProductResponse;

import java.util.List;

import static store.common.constant.GlobalMessage.BLANK_AND_NEW_LINE;
import static store.common.constant.GlobalMessage.NEW_LINE;

public class ConsoleOutputView {

    public void printOnboardingMessage(final String message) {
        System.out.println(message);
    }

    public void printErrorMessage(final String errorMessage) {
        System.out.println(OutputFormatter.formatErrorMessage(errorMessage));
    }

    public void printProductsInformation(final List<ProductResponse> products) {
        System.out.print("안녕하세요. W편의점입니다." +
                NEW_LINE.get() +
                "현재 보유하고 있는 상품입니다." +
                BLANK_AND_NEW_LINE.get());

        String formattedProducts = OutputFormatter.formatProducts(products);
        System.out.println(formattedProducts + BLANK_AND_NEW_LINE.get());
    }

}
