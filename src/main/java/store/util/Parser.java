package store.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {

    private static final String DELIMITERS = ",";

    private Parser() {
    }

    public static List<String> parseDelimitersString(String delimitedString) {
        try {
            return Arrays.stream(delimitedString.split(DELIMITERS, -1))
                    .toList();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("유효한 문자가 들어와야합니다.");
        }
    }

    public static int parseStringToInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("유효한 숫자가 입력되어야 합니다.");
        }
    }

    public static LocalDate parseStringToLocalDate(String input) {
        try {
            return LocalDate.parse(input);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("유효한 날짜가 입력되어야 합니다.");
        }
    }

    public static String parseIntToString(int input) {
        return String.valueOf(input);
    }
}
