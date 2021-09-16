package by.ArseniyTY.tasks.math_tasks;

import by.ArseniyTY.exceptions.IncorrectTaskConditionsException;
import by.ArseniyTY.exceptions.NotHandledEnumElementException;
import by.ArseniyTY.quizer.Result;

public class RealExpressionMathTask extends ExpressionMathTask implements RealMathTask {
    private int precision;
    private final double number1;
    private final double number2;

    public RealExpressionMathTask(int precision, double number1, double number2, MathOperatorType type)
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
        return super.validate(answer);
    }

    @Override
    public String getAnswer() throws NotHandledEnumElementException {
        double answer;
        switch (getOperatorType()) {
            case SUM -> answer = number1 + number2;
            case DIFFERENCE -> answer = number1 - number2;
            case MULTIPLICATION -> answer = number1 * number2;
            case DIVISION -> answer = number1 / number2;
            default -> throw new NotHandledEnumElementException();
        }
        return DoubleRounder.GetDoubleStringWithPrecision(answer, precision);
    }
}
