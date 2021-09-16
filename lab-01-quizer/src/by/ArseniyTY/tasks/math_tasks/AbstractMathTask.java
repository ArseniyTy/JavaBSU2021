package by.ArseniyTY.tasks.math_tasks;

import by.ArseniyTY.exceptions.IncorrectTaskConditionsException;
import by.ArseniyTY.exceptions.NotHandledEnumElementException;
import by.ArseniyTY.quizer.Result;

import java.util.Objects;

abstract class AbstractMathTask implements MathTask {
    private final MathOperatorType operatorType;

    AbstractMathTask(MathOperatorType type) {
        this.operatorType = type;
    }

    protected abstract String getNumber1();
    protected abstract String getNumber2();

    @Override
    public Result validate(String answer) throws NotHandledEnumElementException {
        return (Objects.equals(answer, getAnswer()) || Objects.equals(getAnswer(), "NOT_ZERO")) ?
                Result.OK : Result.WRONG;
    }

    protected MathOperatorType getOperatorType() {
        return operatorType;
    }

    protected String getOperatorStringRepresentation() throws NotHandledEnumElementException {
        switch (operatorType) {
            case SUM -> {
                return "+";
            }
            case DIFFERENCE -> {
                return "-";
            }
            case MULTIPLICATION -> {
                return "*";
            }
            case DIVISION -> {
                return "/";
            }
            default -> throw new NotHandledEnumElementException();
        }
    }
}
