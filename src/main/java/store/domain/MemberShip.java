package store.domain;

import java.util.function.Function;

public enum MemberShip {
    DISCOUNT(price -> (int) Math.floor(Math.min(8000, price * 0.3f)));

    private final Function<Integer, Integer> discountFunction;

    MemberShip(Function<Integer, Integer> discountFunction) {
        this.discountFunction = discountFunction;
    }

    public int applyDiscount(int price) {
        return discountFunction.apply(price);
    }
}
