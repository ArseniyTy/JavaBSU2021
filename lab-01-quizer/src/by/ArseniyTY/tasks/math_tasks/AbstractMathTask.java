package by.ArseniyTY.tasks.math_tasks;

import by.ArseniyTY.exceptions.IncorrectTaskConditionsException;
import by.ArseniyTY.exceptions.NotHandledEnumElementException;
import by.ArseniyTY.quizer.Result;

import java.util.Objects;

abstract class AbstractMathTask implements MathTask {
    protected int precision;
    protected final double number1;
    protected final double number2;
    protected final MathOperatorType operatorType;

    AbstractMathTask(int precision, double number1, double number2, MathOperatorType type)
            throws NotHandledEnumElementException, IncorrectTaskConditionsException {
        setPrecision(precision);
        this.number1 = DoubleRounder.GetDoubleWithPrecision(number1, precision);
        this.number2 = DoubleRounder.GetDoubleWithPrecision(number2, precision);
        this.operatorType = type;
        if (!isArithmeticCorrect()) {
            throw new IncorrectTaskConditionsException();
        }
    }

    public void setPrecision(int precision) throws IllegalArgumentException {
        if (precision <= 0) {
            throw new IllegalArgumentException();
        }
        this.precision = precision;
    }

    protected String getNumber1() {
        return DoubleRounder.GetDoubleStringWithPrecision(number1, precision);
    }

    protected String getNumber2() {
        return DoubleRounder.GetDoubleStringWithPrecision(number2, precision);
    }

    @Override
    public Result validate(String answer) throws NotHandledEnumElementException {
        double num;
        try {
            num = Double.parseDouble(answer);
        } catch (NumberFormatException ex){
            return Result.INCORRECT_INPUT;
        }
        if (num != DoubleRounder.GetDoubleWithPrecision(num, precision)) {
            return Result.INCORRECT_INPUT;
        }
        return (Objects.equals(answer, getAnswer()) || Objects.equals(getAnswer(), "NOT_ZERO")) ?
                Result.OK : Result.WRONG;
    }

    protected String getOperatorStringRepresentation() {
        return switch (operatorType) {
            case SUM -> "+";
            case DIFFERENCE -> "-";
            case MULTIPLICATION -> "*";
            case DIVISION -> "/";
        };
    }
}
