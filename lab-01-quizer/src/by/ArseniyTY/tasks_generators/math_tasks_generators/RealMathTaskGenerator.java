package by.ArseniyTY.tasks_generators.math_tasks_generators;

import by.ArseniyTY.tasks.math_tasks.DoubleRounder;

import java.util.concurrent.ThreadLocalRandom;

interface RealMathTaskGenerator extends MathTaskGenerator {
    double getMinNumber();
    double getMaxNumber();

    default double generateNumber(int precision) {
        return DoubleRounder.GetDoubleWithPrecision(
                ThreadLocalRandom.current().nextDouble(getMinNumber(), getMaxNumber() + Double.MIN_VALUE),
                precision);
    }

    default void setPrecision(int precision) throws IllegalArgumentException {
        if (precision <= 0) {
            throw new IllegalArgumentException();
        }
    }

    default void setMinAndMaxNumber(double minNumber, double maxNumber, boolean isDivisionTheOnlyOperator)
            throws IllegalArgumentException {
        if (minNumber > maxNumber) {
            throw new IllegalArgumentException();
        }
        if (Double.compare(maxNumber, 0) == 0 && Double.compare(minNumber, 0) == 0 && isDivisionTheOnlyOperator) {
            throw new IllegalArgumentException();
        }
    }
}
