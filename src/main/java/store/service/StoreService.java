package store.service;

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
}
