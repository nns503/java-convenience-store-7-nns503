package store.util;

import java.text.DecimalFormat;

public class Formatter {

    public static final String DECIMAL_PATTERN = "###,###";
    public static final String ERROR = "[ERROR]";

    private Formatter() {
    }

    public static String formatToCurrency(int amount) {
        return new DecimalFormat(DECIMAL_PATTERN).format(amount);
    }

    public static String formatToErrorMessage(String message) {
        return String.format("%s %s",ERROR, message);
    }
}
