package store.domain;

import static store.util.NumberFormatter.formatNumber;

import java.time.LocalDateTime;

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
            return String.format("- %s %s원 재고 없음 %s%n",
                name, formatNumber(price), convertedPromotion);
        }
        return String.format("- %s %s원 %d개 %s%n", name, formatNumber(price),
            quantity, convertedPromotion);
    }

    public boolean hasPromotion() {
        return promotion != null;
    }

    public boolean isPromotionTime(LocalDateTime time) {
        return promotion.isPromotionTime(time);
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

    public int getPromotionBuy() {
        return promotion.getBuy();
    }

    public int getPromotionGet() {
        return promotion.getGet();
    }

    public void decreaseQuantity(Integer count) {
        this.quantity = Math.max(quantity - count, 0);
    }
}
