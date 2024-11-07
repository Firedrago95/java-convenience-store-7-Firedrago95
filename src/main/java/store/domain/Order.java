package store.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import store.util.OrderValidator;

public class Order {

    private final Map<String, Integer> order;

    public Order(Map<String, Integer> order, List<Product> products) {
        OrderValidator.validateOrderValues(order, products);
        this.order = order;
    }

    public Set<String> getOrderNames() {
        return order.keySet();
    }

    public List<String> getLessCountOrders(List<Product> availableProduct) {
        List<String> lessCount = new ArrayList<>();
        for (String orderName : getOrderNames()) {
            makeLessCount(availableProduct, lessCount, orderName);
        }
        return lessCount;
    }

    private void makeLessCount(List<Product> products, List<String> lessCount, String orderName) {
        int count = order.get(orderName);
        boolean isEligibleForPromotion = products.stream()
            .anyMatch(product -> product.getName().equals(orderName) &&
                count + 1 <= product.getQuantity() &&
                count % (product.getPromotionBuy() + product.getPromotionGet())
                    == product.getPromotionBuy());
        if (isEligibleForPromotion) {
            lessCount.add(orderName);
        }
    }

    public void addCount(List<String> filteredLessCountOrders) {
        filteredLessCountOrders.stream()
            .forEach(f ->{
                order.put(f, order.get(f) + 1);
            });
    }
}
