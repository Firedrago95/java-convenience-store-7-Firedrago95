package store.domain;

import static store.util.NumberFormatter.formatNumber;

public class Product {

    private String name;
    private int price;
    private int quantity;
    private Promotion promotion;

    public Product(String name, int price, int quantity, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    @Override
    public String toString() {
        String convertedPromotion = "";
        if (promotion != null) {
            convertedPromotion = promotion.getName();
        }
        if (quantity == 0) {
            return String.format("- %s %s원 재고 없음%n", name, formatNumber(price));
        }
        return String.format("- %s %s원 %d개 %s%n", name, formatNumber(price),
            quantity, convertedPromotion);
    }

    public boolean hasPromotion() {
        return promotion != null;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
