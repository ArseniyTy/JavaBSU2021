package by.ArseniyTY.tasks_generators.math_tasks_generators;

import by.ArseniyTY.exceptions.IncorrectTaskConditionsException;
import by.ArseniyTY.exceptions.NotHandledEnumElementException;
import by.ArseniyTY.quizer.Task;
import by.ArseniyTY.tasks.math_tasks.MathOperatorType;
import by.ArseniyTY.tasks.math_tasks.IntegerExpressionMathTask;
import by.ArseniyTY.tasks.math_tasks.RealExpressionMathTask;

import java.util.EnumSet;

public class IntegerExpressionMathTaskGenerator extends ExpressionMathTaskGenerator
                                                implements IntegerMathTaskGenerator {
    private int minNumber;
    private int maxNumber;

    public IntegerExpressionMathTaskGenerator(int minNumber, int maxNumber, EnumSet<MathOperatorType> operators) {
        super(operators);
        setMinAndMaxNumber(minNumber, maxNumber, isDivisionTheOnlyOperator());
    }

    @Override
    public void setMinAndMaxNumber(int minNumber, int maxNumber, boolean isDivisionTheOnlyOperator)
            throws IllegalArgumentException {
        IntegerMathTaskGenerator.super.setMinAndMaxNumber(minNumber, maxNumber, isDivisionTheOnlyOperator);
        this.minNumber = minNumber;
        this.maxNumber = maxNumber;
    }

    @Override
    public Task generate() throws NotHandledEnumElementException {
        IntegerExpressionMathTask task;
        do {
            try {
                task =  new IntegerExpressionMathTask(
                        IntegerMathTaskGenerator.super.generateNumber(),
                        IntegerMathTaskGenerator.super.generateNumber(),
                        generateOperator());
            } catch (IncorrectTaskConditionsException ignored) {
                task = null;
            }
        } while (task == null);
        return task;
    }

    @Override
    public int getMinNumber() {
        return minNumber;
    }

    @Override
    public int getMaxNumber() {
        return maxNumber;
    }
}
