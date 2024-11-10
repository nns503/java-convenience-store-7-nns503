package store;

import store.config.RepositoryConfig;
import store.controller.controller.MainController;

public class Application {
    public static void main(String[] args) {
        RepositoryConfig repositoryConfig = new RepositoryConfig();
        MainController mainController = new MainController();
        repositoryConfig.init();
        mainController.start();
    }
}
