package store.domain;

import java.util.List;
import java.util.Map;
import store.util.OrderValidator;

public class Order {

    private final Map<String, Integer> order;

    public Order(Map<String, Integer> order, List<Product> products) {
        OrderValidator.validateOrderValues(order, products);
        this.order = order;
    }

}
