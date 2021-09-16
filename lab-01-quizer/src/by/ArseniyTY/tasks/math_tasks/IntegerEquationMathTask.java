package by.ArseniyTY.tasks.math_tasks;

import by.ArseniyTY.exceptions.IncorrectTaskConditionsException;
import by.ArseniyTY.exceptions.NotHandledEnumElementException;
import by.ArseniyTY.quizer.Result;

import java.util.Objects;

public class IntegerEquationMathTask extends EquationMathTask implements IntegerMathTask {
    private final int number1;
    private final int number2;

    public IntegerEquationMathTask(int number1, int number2, MathOperatorType type)
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
        if (Objects.equals(getAnswer(), "NOT_ZERO") && Integer.parseInt(answer) == 0) {
            return Result.WRONG;
        }
        return super.validate(answer);
    }

    @Override
    public String getAnswer() throws NotHandledEnumElementException {
        int answer;
        switch (getOperatorType()) {
            case SUM -> answer = number2 - number1;
            case DIFFERENCE -> answer = number1 - number2;
            case MULTIPLICATION -> {
                if (number1 == 0 && number2 == 0) {
                    return "NOT_ZERO";
                }
                answer = number2 / number1;
            }
            case DIVISION -> {
                if (number1 == 0 && number2 == 0) {
                    return "NOT_ZERO";
                }
                answer = number1 / number2;
                if (answer == 0) {
                    throw new ArithmeticException();
                }
            }
            default -> throw new NotHandledEnumElementException();
        }
        return Integer.toString(answer);
    }
}
