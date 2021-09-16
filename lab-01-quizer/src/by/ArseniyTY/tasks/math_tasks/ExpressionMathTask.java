package by.ArseniyTY.tasks.math_tasks;

import by.ArseniyTY.exceptions.IncorrectTaskConditionsException;
import by.ArseniyTY.exceptions.NotHandledEnumElementException;

abstract class ExpressionMathTask extends AbstractMathTask {
    public ExpressionMathTask(int precision, double number1, double number2, MathOperatorType type)
            throws NotHandledEnumElementException, IncorrectTaskConditionsException {
        super(precision, number1, number2, type);
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

    @Override
    public String getText() {
        return getNumber1() + getOperatorStringRepresentation() + getNumber2() + "=?";
    }
}
