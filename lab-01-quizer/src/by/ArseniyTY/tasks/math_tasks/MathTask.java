package by.ArseniyTY.tasks.math_tasks;

import by.ArseniyTY.exceptions.NotHandledEnumElementException;
import by.ArseniyTY.quizer.Task;

interface MathTask extends Task {
    String getAnswer() throws NotHandledEnumElementException;

    default boolean isArithmeticCorrect() throws NotHandledEnumElementException {
        try {
            getAnswer();
        } catch (ArithmeticException | NumberFormatException ex) {
            return false;
        }
        return true;
    }

    interface Generator extends Task.Generator {
        double getMinNumber();
        double getMaxNumber();

        default double getDiffNumber() {
            return getMaxNumber() - getMinNumber();
        }
    }
}
