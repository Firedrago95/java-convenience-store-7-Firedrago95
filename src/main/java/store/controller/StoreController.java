package store.controller;

import store.service.StoreService;
import store.view.OutputView;

public class StoreController {

    private StoreService service = new StoreService();

    public void run() {
        OutputView.printWelcome();
        service.createProducts();
    }
}
