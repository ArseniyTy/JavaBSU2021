package by.ArseniyTY.tasks_generators.math_tasks_generators;

import java.util.concurrent.ThreadLocalRandom;

interface IntegerMathTaskGenerator extends MathTaskGenerator {
    int getMinNumber();
    int getMaxNumber();

    default int generateNumber() {
        return ThreadLocalRandom.current().nextInt(getMinNumber(), getMaxNumber() + 1);
    }

    default void setMinAndMaxNumber(int minNumber, int maxNumber, boolean isDivisionTheOnlyOperator)
            throws IllegalArgumentException {
        if (minNumber > maxNumber) {
            throw new IllegalArgumentException();
        }
        if (maxNumber == 0 && minNumber == 0 && isDivisionTheOnlyOperator) {
            throw new IllegalArgumentException();
        }
    }
}
