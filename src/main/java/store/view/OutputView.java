package store.view;

public class OutputView {

    public static final String WELECOME_MESSAGE = "안녕하세요. W편의점입니다.\n"
                                                + "현재 보유하고 있는 상품입니다.\n";

    public static void printWelcome() {
        System.out.println(WELECOME_MESSAGE);
    }

    public static void printProducts(String products) { System.out.println(products); }

    public static void printReceipt(String receipt) {
        System.out.println(receipt);
    }
}
