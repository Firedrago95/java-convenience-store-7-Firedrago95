package store.controller;

import java.util.ArrayList;
import java.util.List;
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

        List<String> lessCountOrders = service.getLessCountOrders();
        List<String> filteredLessCountOrders = new ArrayList<>();
        for (String lessCountOrder : lessCountOrders) {
            if (readAddCount(lessCountOrder)) {
                filteredLessCountOrders.add(lessCountOrder);
            }
        }
        service.addCount(filteredLessCountOrders);
    }

    private void createOrder() {
        try {
            service.createOrder(InputView.readOrder());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            createOrder();
        }
    }

    private static boolean readAddCount(String lessCountOrder) {
        try {
            return InputView.readAddCount(lessCountOrder);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return readAddCount(lessCountOrder);
        }
    }
}
