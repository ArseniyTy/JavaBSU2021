package by.ArseniyTY.tasks_generators.math_tasks_generators;

import by.ArseniyTY.tasks.math_tasks.DoubleRounder;
import by.ArseniyTY.tasks.math_tasks.MathOperatorType;

import java.util.EnumSet;
import java.util.concurrent.ThreadLocalRandom;

abstract class AbstractMathTaskGenerator implements MathTaskGenerator {
    protected int precision;
    private double minNumber;
    private double maxNumber;
    private final EnumSet<MathOperatorType> operators;

    @Override
    public double getMinNumber() {
        return minNumber;
    }

    @Override
    public double getMaxNumber() {
        return maxNumber;
    }

    AbstractMathTaskGenerator(int precision, double minNumber, double maxNumber,
                                     EnumSet<MathOperatorType> operators) {
        setPrecision(precision);
        setMinAndMaxNumber(minNumber, maxNumber, isDivisionTheOnlyOperator());
        this.operators = operators;
    }

    private void setPrecision(int precision) throws IllegalArgumentException {
        if (precision <= 0) {
            throw new IllegalArgumentException();
        }
        this.precision = precision;
    }

    private void setMinAndMaxNumber(double minNumber, double maxNumber, boolean isDivisionTheOnlyOperator)
            throws IllegalArgumentException {
        if (minNumber > maxNumber) {
            throw new IllegalArgumentException();
        }
        if (Double.compare(maxNumber, 0) == 0 && Double.compare(minNumber, 0) == 0 && isDivisionTheOnlyOperator) {
            throw new IllegalArgumentException();
        }
        this.minNumber = minNumber;
        this.maxNumber = maxNumber;
    }

    protected MathOperatorType generateOperator() {
        return operators.toArray(new MathOperatorType[operators.size()])
                [ThreadLocalRandom.current().nextInt(0, operators.size())];
    }

    private boolean isDivisionTheOnlyOperator() {  // TODO: change
        return operators.contains(MathOperatorType.DIVISION) && operators.size() == 1;
    }

    protected double generateNumber() {
        return DoubleRounder.GetDoubleWithPrecision(
                ThreadLocalRandom.current().nextDouble(
                        getMinNumber(), getMaxNumber() + Double.MIN_VALUE),
                precision);
    }
}
