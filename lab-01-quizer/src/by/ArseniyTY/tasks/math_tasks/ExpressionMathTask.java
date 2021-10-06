package by.ArseniyTY.tasks.math_tasks;

import by.ArseniyTY.exceptions.IncorrectTaskConditionsException;
import by.ArseniyTY.quizer.Task;

import java.util.EnumSet;

public class ExpressionMathTask extends AbstractMathTask {
    public static class Generator extends AbstractMathTask.Generator {
        public Generator(double minNumber, double maxNumber, int precision, EnumSet<MathOperator> operators) {
            super(minNumber, maxNumber, precision, operators);
        }

        public Generator(double minNumber, double maxNumber, EnumSet<MathOperator> operators) {
            super(minNumber, maxNumber, operators);
        }

        @Override
        public Task generate() {
            ExpressionMathTask task;
            do {
                try {
                    task = new ExpressionMathTask(generateNumber(), generateNumber(), precision, generateOperator());
                } catch (IncorrectTaskConditionsException ignored) {
                    task = null;
                }
            } while (task == null);
            return task;
        }
    }

    public ExpressionMathTask(double number1, double number2, int precision, MathOperator type)
            throws IncorrectTaskConditionsException {
        super(number1, number2, precision, type);
    }

    public ExpressionMathTask(double number1, double number2, MathOperator type)
            throws IncorrectTaskConditionsException {
        super(number1, number2, type);
    }

    @Override
    public String getText() {
        return getNumber1() + operatorType.toString() + getNumber2() + "=?";
    }

    @Override
    public String getAnswer() {
        return DoubleRounder.GetDoubleStringWithPrecision(operatorType.getResult(number1, number2), precision);
    }
}
