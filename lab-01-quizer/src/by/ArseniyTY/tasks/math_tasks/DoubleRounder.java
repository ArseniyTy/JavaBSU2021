package by.ArseniyTY.tasks.math_tasks;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class DoubleRounder {
    private DoubleRounder() {}

    public static double GetDoubleWithPrecision(double number, int precision) {
        return BigDecimal.valueOf(number)
                .setScale(precision, RoundingMode.HALF_UP)
                .doubleValue();
    }

    public static String GetDoubleStringWithPrecision(double number, int precision) {
        return BigDecimal.valueOf(number)
                .setScale(precision, RoundingMode.HALF_UP)
                .toString();
    }
}
