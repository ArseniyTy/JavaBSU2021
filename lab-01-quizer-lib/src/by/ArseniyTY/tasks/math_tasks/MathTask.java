package by.ArseniyTY.tasks.math_tasks;

import by.ArseniyTY.quizer.Task;

public interface MathTask extends Task {
    interface Generator extends Task.Generator {
        double getMinNumber();
        double getMaxNumber();

        default double getDiffNumber() {
            return getMaxNumber() - getMinNumber();
        }
    }

    String getAnswer();

    default boolean isArithmeticCorrect() {
        try {
            getAnswer();
        } catch (ArithmeticException | NumberFormatException ex) {
            return false;
        }
        return true;
    }
}
