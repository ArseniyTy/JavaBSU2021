package by.ArseniyTY.tasks_generators.math_tasks_generators;

import by.ArseniyTY.exceptions.IncorrectTaskConditionsException;
import by.ArseniyTY.exceptions.NotHandledEnumElementException;
import by.ArseniyTY.quizer.Task;
import by.ArseniyTY.tasks.math_tasks.MathOperatorType;
import by.ArseniyTY.tasks.math_tasks.RealEquationMathTask;
import by.ArseniyTY.tasks.math_tasks.RealExpressionMathTask;

import java.util.EnumSet;

public class RealExpressionMathTaskGenerator extends ExpressionMathTaskGenerator implements RealMathTaskGenerator {
    private int precision;
    private double minNumber;
    private double maxNumber;

    // TODO: don't generate /0 tasks

    public RealExpressionMathTaskGenerator(int precision, double minNumber, double maxNumber,
                                           EnumSet<MathOperatorType> operators) {
        super(operators);
        setPrecision(precision);
        setMinAndMaxNumber(minNumber, maxNumber, isDivisionTheOnlyOperator());
    }

    @Override
    public void setPrecision(int precision) throws IllegalArgumentException {
        RealMathTaskGenerator.super.setPrecision(precision);
        this.precision = precision;
    }

    @Override
    public void setMinAndMaxNumber(double minNumber, double maxNumber, boolean isDivisionTheOnlyOperator)
            throws IllegalArgumentException {
        RealMathTaskGenerator.super.setMinAndMaxNumber(minNumber, maxNumber, isDivisionTheOnlyOperator);
        this.minNumber = minNumber;
        this.maxNumber = maxNumber;
    }

    @Override
    public Task generate() throws NotHandledEnumElementException {
        RealExpressionMathTask task;
        do {
            try {
                task = new RealExpressionMathTask(
                        precision,
                        RealMathTaskGenerator.super.generateNumber(precision),
                        RealMathTaskGenerator.super.generateNumber(precision),
                        generateOperator());
            } catch (IncorrectTaskConditionsException ignored) {
                task = null;
            }
        } while (task == null);
        return task;
    }

    @Override
    public double getMinNumber() {
        return minNumber;
    }

    @Override
    public double getMaxNumber() {
        return maxNumber;
    }
}