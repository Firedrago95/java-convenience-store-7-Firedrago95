package store.domain;

import java.util.function.Function;

public enum MemberShip {
    DISCOUNT(price -> (int) Math.min(Constants.MAX_DISCOUNT_PRICE, price * Constants.DISCOUNT_RATE));

    private final Function<Integer, Integer> discountFunction;

    MemberShip(Function<Integer, Integer> discountFunction) {
        this.discountFunction = discountFunction;
    }

    public int applyDiscount(int price) {
        return discountFunction.apply(price);
    }

    private static class Constants {

        public static final int MAX_DISCOUNT_PRICE = 8000;
        public static final float DISCOUNT_RATE = 0.3f;
    }
}
