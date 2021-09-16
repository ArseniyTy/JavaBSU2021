package by.ArseniyTY.tasks.math_tasks;

import by.ArseniyTY.exceptions.IncorrectTaskConditionsException;
import by.ArseniyTY.exceptions.NotHandledEnumElementException;
import by.ArseniyTY.quizer.Result;

import java.util.Objects;

public class RealEquationMathTask extends EquationMathTask implements RealMathTask {
    private int precision;
    private final double number1;
    private final double number2;

    public RealEquationMathTask(int precision, double number1, double number2, MathOperatorType type)
            throws NotHandledEnumElementException, IncorrectTaskConditionsException {
        super(type);
        setPrecision(precision);
        this.number1 = DoubleRounder.GetDoubleWithPrecision(number1, precision);
        this.number2 = DoubleRounder.GetDoubleWithPrecision(number2, precision);
        if (!isArithmeticCorrect()) {
            throw new IncorrectTaskConditionsException();
        }
    }

    @Override
    protected String getNumber1() {
        return DoubleRounder.GetDoubleStringWithPrecision(number1, precision);
    }

    @Override
    protected String getNumber2() {
        return DoubleRounder.GetDoubleStringWithPrecision(number2, precision);
    }

    @Override
    public int getPrecision() {
        return precision;
    }

    @Override
    public void setPrecision(int precision) throws IllegalArgumentException {
        RealMathTask.super.setPrecision(precision);
        this.precision = precision;
    }

    @Override
    public Result validate(String answer) throws NotHandledEnumElementException {
        var realMathTaskValidationResult = RealMathTask.super.validate(answer);
        if (realMathTaskValidationResult != Result.OK) {
            return realMathTaskValidationResult;
        }
        if (Objects.equals(getAnswer(), "NOT_ZERO") && Double.compare(Double.parseDouble(answer), 0) == 0) {
            return Result.WRONG;
        }
        return super.validate(answer);
    }

    @Override
    public String getAnswer() throws NotHandledEnumElementException {
        double answer;
        switch (getOperatorType()) {
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
