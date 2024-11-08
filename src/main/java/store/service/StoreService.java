package store.service;

import camp.nextstep.edu.missionutils.DateTimes;
import java.util.List;
import java.util.Map;
import store.domain.Order;
import store.domain.Product;
import store.domain.Products;
import store.domain.ReceiptPrinter;
import store.util.FileConverter;

public class StoreService {

    private Products products;
    private Order order;
    private ReceiptPrinter printer;

    public void createProducts() {
        FileConverter fileConverter = new FileConverter();
        fileConverter.readPromotionsFile();
        products = new Products(fileConverter.readProductsFile());
    }

    public String makeProductsStatus() {
        return products.toString();
    }

    public void createOrder(Map<String, Integer> input) {
        order = new Order(input, products.getProducts());
    }

    public List<String> getLessCountOrders() {
        List<Product> availableProduct
            = products.findAvailableProduct(order.getOrderNames(),  DateTimes.now());
        return order.getLessCountOrders(availableProduct);
    }

    public void addCount(List<String> filteredLessCountOrders) {
        order.addCount(filteredLessCountOrders);
    }

    public Map<String, Integer> getExceedCountOrders() {
        List<Product> availableProduct
            = products.findAvailableProduct(order.getOrderNames(),  DateTimes.now());
        return order.getExceedCountOrders(availableProduct);
    }

    public void substractCount(Map<String, Integer> orders) {
        order.substractCount(orders);
    }

    public void changeQuantity() {
        products.changeQuantity(order.getOrder());
    }

    public String makeReceipt(boolean askForMemberShip) {
        printer = new ReceiptPrinter(order.getOrder(), products.getProducts(), askForMemberShip);
        return printer.makeReceipt();
    }
}
