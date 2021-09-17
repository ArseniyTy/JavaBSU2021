package by.ArseniyTY.tasks.math_tasks;

import by.ArseniyTY.exceptions.IncorrectTaskConditionsException;
import by.ArseniyTY.exceptions.NotHandledEnumElementException;
import by.ArseniyTY.quizer.Task;

import java.util.EnumSet;

public class EquationMathTask extends AbstractMathTask {
    public static class Generator extends AbstractMathTask.Generator {
        public Generator(int precision, double minNumber, double maxNumber,
                         EnumSet<MathOperatorType> operators) {
            super(precision, minNumber, maxNumber, operators);
        }

        @Override
        public Task generate() throws NotHandledEnumElementException {
            EquationMathTask task;
            do {
                try {
                    task = new EquationMathTask(precision, generateNumber(), generateNumber(), generateOperator());
                } catch (IncorrectTaskConditionsException ignored) {
                    task = null;
                }
            } while (task == null);
            return task;
        }
    }

    public EquationMathTask(int precision, double number1, double number2, MathOperatorType type)
            throws NotHandledEnumElementException, IncorrectTaskConditionsException {
        super(precision, number1, number2, type);
    }

    @Override
    public String getText() {
        return getNumber1() + getOperatorStringRepresentation() + "x=" + getNumber2();
    }

    @Override
    public String getAnswer() throws NotHandledEnumElementException {
        double answer;
        switch (operatorType) {
            case SUM -> answer = number2 - number1;
            case DIFFERENCE -> answer = number1 - number2;
            case MULTIPLICATION -> {
                if (Double.compare(number1, 0) == 0 && Double.compare(number2, 0) == 0) {
                    return "NOT_ZERO";
                }
                answer = number2 / number1;
            }
            case DIVISION -> {
                if (Double.compare(number1, 0) == 0 && Double.compare(number2, 0) == 0) {
                    return "NOT_ZERO";
                }
                answer = number1 / number2;
                if (Double.compare(answer, 0) == 0) {
                    throw new ArithmeticException();
                }
            }
            default -> throw new NotHandledEnumElementException();
        }
        return DoubleRounder.GetDoubleStringWithPrecision(answer, precision);
    }
}
