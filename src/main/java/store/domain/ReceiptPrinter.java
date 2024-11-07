package store.domain;

import static store.util.NumberFormatter.formatNumber;

import camp.nextstep.edu.missionutils.DateTimes;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReceiptPrinter {

    private Map<String, Integer> order;
    private List<Product> products;
    private boolean appliedMemberShip;

    public ReceiptPrinter(Map<String, Integer> order, List<Product> products,
        boolean appliedMemberShip) {
        this.order = order;
        this.products = products;
        this.appliedMemberShip = appliedMemberShip;
    }

    public String makeReceipt() {
        Map<String, Integer> orderPrice = new HashMap<>();
        Map<String, Integer> promotion = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        makeOrderPart(orderPrice, sb);
        makePromotionPart(promotion, sb);
        makeTotalPrice(orderPrice, promotion, sb);
        return sb.toString();
    }

    private void makeOrderPart(Map<String,Integer> orderPrice, StringBuilder sb) {
        sb.append("==============W 편의점================\n");
        sb.append("상품명              수량      금액     \n");
        for (String name : order.keySet()) {
            int quantity = order.get(name);
            int price = getPrice(name) * quantity;
            orderPrice.put(name, price);
            sb.append(String.format("%-17s%-8d%-7s\n",name, quantity, formatNumber(price)));
        }
    }

    private int getPrice(String name) {
        return products.stream()
            .filter(product -> product.getName().equals(name))
            .mapToInt(product -> product.getPrice())
            .findFirst()
            .getAsInt();
    }

    private void makePromotionPart(Map<String, Integer> promotion, StringBuilder sb) {
        sb.append("=============증    정===============\n");
        for (String name : order.keySet()) {
            boolean hasPromotion = isHasPromotion(name);
            if (hasPromotion) {
                Product findProduct = getFindProduct(name);
                int promotionCount = getPromotionCount(name, findProduct);
                promotion.put(name, promotionCount);
                sb.append(String.format("%-17s%-15d\n", name, promotionCount));
            }
        }
    }

    private boolean isHasPromotion(String name) {
        boolean b = products.stream()
            .anyMatch(product -> product.getName().equals(name)
                && product.hasPromotion()
                && product.isPromotionTime(DateTimes.now()));
        return b;
    }

    private Product getFindProduct(String name) {
        return products.stream()
            .filter(product -> product.getName().equals(name) && product.hasPromotion())
            .findFirst()
            .get();
    }

    private int getPromotionCount(String name, Product findProduct) {
            return Math.min(order.get(name), findProduct.getQuantity())
                / (findProduct.getPromotionBuy() + findProduct.getPromotionGet());
    }

    private void makeTotalPrice(Map<String, Integer> orderPrice, Map<String, Integer> promotion,
        StringBuilder sb) {
        sb.append("====================================\n");
        int sum = getTotalOrderPrice(orderPrice, sb);
        int promotionSum = getPromotionSum(promotion, sb);
        int memberShip = getMemberShip(sb, promotion, sum);
        sb.append(String.format("%-25s%7s\n", "내실 돈",formatNumber(sum - promotionSum - memberShip)));
    }

    private int getTotalOrderPrice(Map<String, Integer> orderPrice, StringBuilder sb) {
        int size = order.values().stream().mapToInt(Integer::intValue).sum();
        int sum = orderPrice.values().stream().mapToInt(Integer::intValue).sum();
        sb.append(String.format("%-17s%-8d%7s\n","총구매액", size, formatNumber(sum)));
        return sum;
    }

    private int getPromotionSum(Map<String, Integer> promotion, StringBuilder sb) {
        int promotionSum = 0;
        for (String name : promotion.keySet()) {
            Product findProduct = products.stream()
                .filter(product -> product.getName().equals(name))
                .findFirst().get();
            promotionSum += promotion.get(name) * findProduct.getPrice();
        }
        sb.append(String.format("%-25s%7s\n", "행사할인","-"+formatNumber(promotionSum)));
        return promotionSum;
    }

    private int getMemberShip(StringBuilder sb, Map<String, Integer> promotion, int sum) {
        int memberShip = 0;
        int promotionTotal = getPromotionTotal(promotion);
        if (appliedMemberShip) {
            memberShip = MemberShip.DISCOUNT.applyDiscount(sum - promotionTotal);
        }
        sb.append(String.format("%-25s%7s\n", "멤버십할인","-"+formatNumber(memberShip)));
        return memberShip;
    }

    private int getPromotionTotal(Map<String, Integer> promotion) {
        int promotionTotal = 0;
        for (String name : promotion.keySet()) {
            Product findProduct = products.stream()
                .filter(product -> product.getName().equals(name))
                .findFirst().get();
            promotionTotal += promotion.get(name)
                * (findProduct.getPromotionBuy() + findProduct.getPromotionGet())
                * findProduct.getPrice();
        }
        return promotionTotal;
    }
}
