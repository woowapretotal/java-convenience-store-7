package store;

import camp.nextstep.edu.missionutils.Console;
import store.application.controller.StoreController;
import store.config.AppConfig;

public class Application {
    public static void main(String[] args) {

        try {
            AppConfig appConfig = new AppConfig();
            StoreController storeController = appConfig.storeController();
            storeController.orderWithRetrying();
        } finally {
            Console.close();
        }
    }
}
