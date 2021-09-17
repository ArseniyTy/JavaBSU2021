package by.ArseniyTY.tasks.math_tasks;

import by.ArseniyTY.exceptions.IncorrectTaskConditionsException;
import by.ArseniyTY.exceptions.NotHandledEnumElementException;
import by.ArseniyTY.quizer.Task;

import java.util.EnumSet;

public class EquationMathTask extends AbstractMathTask {
    public static class Generator extends AbstractMathTask.Generator {
        public Generator(double minNumber, double maxNumber, int precision, EnumSet<MathOperatorType> operators) {
            super(minNumber, maxNumber, precision, operators);
        }

        public Generator(double minNumber, double maxNumber, EnumSet<MathOperatorType> operators) {
            super(minNumber, maxNumber, operators);
        }

        @Override
        public Task generate() throws NotHandledEnumElementException {
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

    public EquationMathTask(double number1, double number2, int precision, MathOperatorType type)
            throws NotHandledEnumElementException, IncorrectTaskConditionsException {
        super(number1, number2, precision, type);
    }

    public EquationMathTask(double number1, double number2, MathOperatorType type)
            throws NotHandledEnumElementException, IncorrectTaskConditionsException {
        super(number1, number2, type);
    }

    @Override
    public String getText() {
        return getNumber1() + operatorType.toString() + "x=" + getNumber2();
    }

    @Override
    public String getAnswer() {
        if ((operatorType == MathOperatorType.MULTIPLICATION
          || operatorType == MathOperatorType.DIVISION)
                && Double.compare(number1, 0) == 0
                && Double.compare(number2, 0) == 0) {
            return "NOT_ZERO";
        }

        double answer = switch (operatorType) {
            case SUM -> number2 - number1;
            case DIFFERENCE -> number1 - number2;
            case MULTIPLICATION -> number2 / number1;
            case DIVISION -> number1 / number2;
        };

        if (operatorType == MathOperatorType.DIVISION && Double.compare(answer, 0) == 0) {
            throw new ArithmeticException();
        }
        return DoubleRounder.GetDoubleStringWithPrecision(answer, precision);
    }
}
