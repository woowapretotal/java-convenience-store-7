package store.application.controller;

import store.application.controller.adapter.ConsoleInputAdapter;
import store.application.service.ProductService;
import store.application.service.response.ProductResponse;
import store.application.view.ConsoleOutputView;

import java.util.List;

public class StoreController extends RetryController {
    private final ProductService productService;
    private final ConsoleInputAdapter inputAdapter;

    public StoreController(final ProductService productService, final ConsoleInputAdapter inputAdapter, final ConsoleOutputView outputView) {
        super(outputView);
        this.productService = productService;
        this.inputAdapter = inputAdapter;
    }

    public void printProducts() {
        List<ProductResponse> productResponses = productService.findAll();
        outputView.printProductsInformation(productResponses);
    }

    public void runWithRetrying() {
        /*XServiceXXXResponse response = retrying(() -> {
            int xx = inputReader.readXX();
            return xService.logic(new XServiceXXXRequest(xx));
        });

        outputView.printXX(response);*/
    }
}
