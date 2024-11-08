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
        return String.format("- %s %s원 %s%s%n",
            name, formatNumber(price), getQuantityText(), getPromotionText());
    }

    private String getQuantityText() {
        if (quantity == 0) {
            return "재고 없음 ";
        }
        return quantity + "개 ";
    }

    private String getPromotionText() {
        if (promotion != null) {
            return promotion.getName();
        }
        return "";
    }

    public boolean hasPromotion() {
        return promotion != null;
    }

    public boolean isPromotionTime(LocalDateTime time) {
        return promotion.isPromotionTime(time);
    }

    public void decreaseQuantity(Integer count) {
        this.quantity = Math.max(quantity - count, 0);
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
}
