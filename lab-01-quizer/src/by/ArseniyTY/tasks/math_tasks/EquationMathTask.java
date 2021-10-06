package by.ArseniyTY.tasks.math_tasks;

import by.ArseniyTY.exceptions.IncorrectTaskConditionsException;
import by.ArseniyTY.quizer.Task;

import java.util.EnumSet;

public class EquationMathTask extends AbstractMathTask {
    public static class Generator extends AbstractMathTask.Generator {
        public Generator(double minNumber, double maxNumber, int precision, EnumSet<MathOperator> operators) {
            super(minNumber, maxNumber, precision, operators);
        }

        public Generator(double minNumber, double maxNumber, EnumSet<MathOperator> operators) {
            super(minNumber, maxNumber, operators);
        }

        @Override
        public Task generate() {
            EquationMathTask task;
            do {
                try {
                    task = new EquationMathTask(generateNumber(), generateNumber(), precision, generateOperator());
                } catch (IncorrectTaskConditionsException ignored) {
                    task = null;
                }
            } while (task == null);
            return task;
        }
    }

    public EquationMathTask(double number1, double number2, int precision, MathOperator type)
            throws IncorrectTaskConditionsException {
        super(number1, number2, precision, type);
    }

    public EquationMathTask(double number1, double number2, MathOperator type) throws IncorrectTaskConditionsException {
        super(number1, number2, type);
    }

    @Override
    public String getText() {
        return getNumber1() + operatorType.toString() + "x=" + getNumber2();
    }

    @Override
    public String getAnswer() {
        if (Double.compare(number1, 0) == 0 && Double.compare(number2, 0) == 0) {
            if (operatorType == MathOperator.MULTIPLICATION) {
                return "ANY";
            } else if (operatorType == MathOperator.DIVISION) {
                return "NOT_ZERO";
            }
        }

        double answer = switch (operatorType) {
            case SUM -> number2 - number1;
            case DIFFERENCE -> number1 - number2;
            case MULTIPLICATION -> number2 / number1;
            case DIVISION -> number1 / number2;
        };

        if (operatorType == MathOperator.DIVISION && Double.compare(answer, 0) == 0) {
            throw new ArithmeticException();
        }
        return DoubleRounder.GetDoubleStringWithPrecision(answer, precision);
    }
}
