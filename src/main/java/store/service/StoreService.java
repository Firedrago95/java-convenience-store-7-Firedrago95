package store.service;

import camp.nextstep.edu.missionutils.DateTimes;
import java.util.List;
import java.util.Map;
import store.domain.Order;
import store.domain.Product;
import store.domain.Products;
import store.util.FileConverter;

public class StoreService {

    private Products products;
    private Order order;

    public void createProducts() {
        FileConverter.readPromotionsFile();
        List<Product> convertedProducts = FileConverter.readProductsFile();
        products = new Products(convertedProducts);
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
}
