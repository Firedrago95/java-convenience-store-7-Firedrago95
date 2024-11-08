package store.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import store.service.StoreService;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {

    private StoreService service = new StoreService();

    public void run() {
        boolean reBuy = true;
        restock();
        while (reBuy) {
            welcomeCustomer();
            createOrder();
            applyPromotion();
            processCheckout();
            reBuy = askForRepurchase();
        }
    }

    private void restock() {
        service.createProducts();
    }

    private void welcomeCustomer() {
        OutputView.printWelcome();
        OutputView.printProducts(service.makeProductsStatus());
    }

    private void createOrder() {
        try {
            service.createOrder(InputView.readOrder());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            createOrder();
        }
    }

    private void applyPromotion() {
        applyAdditionalCountToLowStockOrders();
        applyReductionsToExcessStockOrders();
    }

    private void applyAdditionalCountToLowStockOrders() {
        List<String> lessCountOrders = service.getLessCountOrders();
        List<String> filteredLessCountOrders = new ArrayList<>();
        for (String lessCountOrder : lessCountOrders) {
            if (readAddCount(lessCountOrder)) {
                filteredLessCountOrders.add(lessCountOrder);
            }
        }
        service.addCount(filteredLessCountOrders);
    }

    private void applyReductionsToExcessStockOrders() {
        Map<String, Integer> exceedCountOrders = service.getExceedCountOrders();
        Map<String, Integer> filteredExceedCountOrders = new HashMap<>();
        for (String name : exceedCountOrders.keySet()) {
            if (!readSubtractCount(name, exceedCountOrders.get(name))) {
                filteredExceedCountOrders.put(name, exceedCountOrders.get(name));
            }
        }
        service.substractCount(filteredExceedCountOrders);
    }

    private void processCheckout() {
        String receipt = service.makeReceipt(isAskForMemberShip());
        OutputView.printReceipt(receipt);
        service.changeQuantity();
    }

    private static boolean askForRepurchase() {
        try {
            return InputView.askForRepurchase();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return askForRepurchase();
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

    private boolean readSubtractCount(String name, Integer count) {
        try {
            return InputView.readSubtractCount(name, count);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return readSubtractCount(name, count);
        }
    }

    private static boolean isAskForMemberShip() {
        try {
            return InputView.askForMemberShip();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return isAskForMemberShip();
        }
    }
}
