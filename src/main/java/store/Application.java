package store;

import store.config.RepositoryConfig;
import store.controller.controller.MainController;

public class Application {
    public static void main(String[] args) {
        RepositoryConfig.INSTANCE.init();
        MainController mainController = new MainController();
        mainController.start();
    }
}
