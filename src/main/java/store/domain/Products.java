package store.domain;

import static store.util.NumberFormatter.formatNumber;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Products {

    private final List<Product> products;

    public Products(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Product product : products) {
            makeProductStatusMessage(sb, product);
        }
        return sb.toString();
    }

    private void makeProductStatusMessage(StringBuilder sb, Product product) {
        sb.append(product.toString());
        String name = product.getName();
        if (hasOneProduct(name) && product.hasPromotion()) {
            sb.append(String.format("- %s %s원 재고 없음%n",
                product.getName(),
                formatNumber(product.getPrice())));
        }
    }

    private boolean hasOneProduct(String name) {
        return products.stream()
            .filter(product -> product.getName().equals(name))
            .count() == 1;
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public List<Product> findAvailableProduct(Set<String> orderNames, LocalDateTime now) {
        List<Product> availableProduct = new ArrayList<>();
        for (String orderName : orderNames) {
            products.stream()
                .filter(product -> product.getName().equals(orderName))
                .filter(product -> product.hasPromotion())
                .filter(product -> product.isPromotionTime(now))
                .forEach(product -> availableProduct.add(product));
        }
        return availableProduct;
    }
}
