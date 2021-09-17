package by.ArseniyTY.tasks.math_tasks;

import by.ArseniyTY.exceptions.IncorrectTaskConditionsException;
import by.ArseniyTY.exceptions.NotHandledEnumElementException;
import by.ArseniyTY.quizer.Task;

import java.util.EnumSet;

public class ExpressionMathTask extends AbstractMathTask {
    public static class Generator extends AbstractMathTask.Generator {
        public Generator(int precision, double minNumber, double maxNumber,
                                           EnumSet<MathOperatorType> operators) {
            super(precision, minNumber, maxNumber, operators);
        }

        @Override
        public Task generate() throws NotHandledEnumElementException {
            ExpressionMathTask task;
            do {
                try {
                    task = new ExpressionMathTask(precision, generateNumber(), generateNumber(), generateOperator());
                } catch (IncorrectTaskConditionsException ignored) {
                    task = null;
                }
            } while (task == null);
            return task;
        }
    }

        public ExpressionMathTask(int precision, double number1, double number2, MathOperatorType type)
            throws NotHandledEnumElementException, IncorrectTaskConditionsException {
        super(precision, number1, number2, type);
    }

    @Override
    public String getText() {
        return getNumber1() + getOperatorStringRepresentation() + getNumber2() + "=?";
    }

    @Override
    public String getAnswer() throws NotHandledEnumElementException {
        double answer = switch (operatorType) {
            case SUM -> number1 + number2;
            case DIFFERENCE -> number1 - number2;
            case MULTIPLICATION -> number1 * number2;
            case DIVISION -> number1 / number2;
        };
        return DoubleRounder.GetDoubleStringWithPrecision(answer, precision);
    }
}
