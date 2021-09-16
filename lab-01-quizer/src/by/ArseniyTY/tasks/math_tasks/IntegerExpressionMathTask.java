package by.ArseniyTY.tasks.math_tasks;

import by.ArseniyTY.exceptions.IncorrectTaskConditionsException;
import by.ArseniyTY.exceptions.NotHandledEnumElementException;
import by.ArseniyTY.quizer.Result;

public class IntegerExpressionMathTask extends ExpressionMathTask implements IntegerMathTask {
    private int number1;
    private int number2;

    public IntegerExpressionMathTask(int number1, int number2, MathOperatorType type)
            throws NotHandledEnumElementException, IncorrectTaskConditionsException {
        super(type);
        this.number1 = number1;
        this.number2 = number2;
        if (!isArithmeticCorrect()) {
            throw new IncorrectTaskConditionsException();
        }
    }

    @Override
    protected String getNumber1() {
        return Integer.toString(number1);
    }

    @Override
    protected String getNumber2() {
        return Integer.toString(number2);
    }

    @Override
    public Result validate(String answer) throws NotHandledEnumElementException {
        var integerMathTaskValidationResult = IntegerMathTask.super.validate(answer);
        if (integerMathTaskValidationResult != Result.OK) {
            return integerMathTaskValidationResult;
        }
        return super.validate(answer);
    }

    @Override
    public String getAnswer() throws NotHandledEnumElementException {
        int answer;
        switch (getOperatorType()) {
            case SUM -> answer = number1 + number2;
            case DIFFERENCE -> answer = number1 - number2;
            case MULTIPLICATION -> answer = number1 * number2;
            case DIVISION -> answer = number1 / number2;
            default -> throw new NotHandledEnumElementException();
        }
        return Integer.toString(answer);
    }
}
