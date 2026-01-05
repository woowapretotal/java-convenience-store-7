package store.application.controller;

import store.application.view.ConsoleOutputView;
import store.common.utils.RetryHandler;

import java.util.function.Supplier;

public abstract class RetryController {
    protected final ConsoleOutputView outputView;

    protected RetryController(ConsoleOutputView outputView) {
        this.outputView = outputView;
    }

    protected final <T> T retrying(Supplier<T> supplier) {
        return RetryHandler.execute(supplier, outputView::printErrorMessage);
    }
}
