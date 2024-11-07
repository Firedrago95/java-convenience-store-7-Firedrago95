package store.domain;

import static store.util.NumberFormatter.formatNumber;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
        return products;
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

    public void changeQuantity(Map<String, Integer> order) {
        for (String name : order.keySet()) {
            int decrease = order.get(name);
            List<Product> filteredProducts = countProduct(name);
            if (oneSizeProductChangeQuantity(decrease, filteredProducts)) {
                continue;
            }
            twoSizeProductsChangeQuantity(decrease, filteredProducts);
        }
    }

    private List<Product> countProduct(String name) {
        List<Product> filteredProducts = products.stream()
            .filter(product -> product.getName().equals(name))
            .collect(Collectors.toList());
        return filteredProducts;
    }

    private static boolean oneSizeProductChangeQuantity(int decrease, List<Product> filteredProducts) {
        if (filteredProducts.size() == 1) {
            Product uniqueProduct = filteredProducts.get(0);
            uniqueProduct.decreaseQuantity(decrease);
            return true;
        }
        return false;
    }

    private static void twoSizeProductsChangeQuantity(int decrease, List<Product> filteredProducts) {
        int promotionQuantity = changePromotionProduct(decrease, filteredProducts);
        decrease = Math.max(0, decrease - promotionQuantity);
        changeNonPromotionProduct(decrease, filteredProducts);
    }

    private static int changePromotionProduct(int decrease, List<Product> filteredProducts) {
        Product promotionProduct = filteredProducts.stream()
            .filter(Product::hasPromotion)
            .findFirst()
            .get();

        int promotionQuantity = promotionProduct.getQuantity();
        promotionProduct.decreaseQuantity(decrease);
        return promotionQuantity;
    }

    private static void changeNonPromotionProduct(int decrease, List<Product> filteredProducts) {
        Product nonPromotionProduct = filteredProducts.stream()
            .filter(product -> !product.hasPromotion())
            .findFirst()
            .get();
        nonPromotionProduct.decreaseQuantity(decrease);
    }
}
