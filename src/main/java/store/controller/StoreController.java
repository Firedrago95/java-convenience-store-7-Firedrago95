package store.controller;

import store.service.StoreService;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {

    private StoreService service = new StoreService();

    public void run() {
        OutputView.printWelcome();
        service.createProducts();

        OutputView.printProducts(service.makeProductsStatus());
        createOrder();
    }

    private void createOrder() {
        try {
            service.createOrder(InputView.readOrder());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            createOrder();
        }
    }
}
