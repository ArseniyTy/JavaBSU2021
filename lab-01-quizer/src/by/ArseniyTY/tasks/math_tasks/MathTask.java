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
}
