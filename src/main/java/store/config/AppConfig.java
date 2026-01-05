package store.config;

import store.application.controller.StoreController;
import store.application.controller.adapter.ConsoleInputAdapter;
import store.application.service.ProductService;
import store.application.view.ConsoleInputView;
import store.application.view.ConsoleOutputView;
import store.domain.ProductRepository;
import store.infra.FileProductRepository;

public class AppConfig {
    // == repository ==
    private final ProductRepository productRepository = new FileProductRepository();

    // == view ==
    private final ConsoleInputView inputView = new ConsoleInputView();
    private final ConsoleOutputView outputView = new ConsoleOutputView();
    private final ConsoleInputAdapter inputAdapter = new ConsoleInputAdapter(inputView, outputView);

    // == domain service ==

    // == application service ==
    private final ProductService productService = new ProductService(productRepository);

    // == controller ==
    private final StoreController storeController = new StoreController(productService, inputAdapter, outputView);

    public ConsoleInputAdapter inputAdapter() {
        return inputAdapter;
    }

    public ConsoleOutputView outputView() {
        return outputView;
    }

    public ProductService xService() {
        return productService;
    }

    public StoreController storeController() {
        return storeController;
    }

}
