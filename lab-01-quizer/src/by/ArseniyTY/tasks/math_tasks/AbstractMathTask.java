package by.ArseniyTY.tasks.math_tasks;

import by.ArseniyTY.exceptions.IncorrectTaskConditionsException;
import by.ArseniyTY.exceptions.NotHandledEnumElementException;
import by.ArseniyTY.quizer.Result;

import java.util.EnumSet;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

abstract class AbstractMathTask implements MathTask {
    static abstract class Generator implements MathTask.Generator {
        protected int precision;
        private double minNumber;
        private double maxNumber;
        private final EnumSet<MathOperatorType> operators;

        Generator(double minNumber, double maxNumber, int precision, EnumSet<MathOperatorType> operators) {
            setMinAndMaxNumber(minNumber, maxNumber);
            setPrecision(precision);
            this.operators = operators;

            if (Double.compare(maxNumber, 0) == 0 && Double.compare(minNumber, 0) == 0 && isDivisionTheOnlyOperator()) {
                throw new IllegalArgumentException();
            }
        }

        Generator(double minNumber, double maxNumber, EnumSet<MathOperatorType> operators) {
            this(minNumber, maxNumber, 0, operators);
        }

        private void setPrecision(int precision) throws IllegalArgumentException {
            if (precision < 0) {
                throw new IllegalArgumentException();
            }
            this.precision = precision;
        }

        private void setMinAndMaxNumber(double minNumber, double maxNumber) throws IllegalArgumentException {
            if (minNumber > maxNumber) {
                throw new IllegalArgumentException();
            }
            this.minNumber = minNumber;
            this.maxNumber = maxNumber;
        }

        @Override
        public double getMinNumber() {
            return minNumber;
        }

        @Override
        public double getMaxNumber() {
            return maxNumber;
        }

        protected MathOperatorType generateOperator() {
            return operators.toArray(new MathOperatorType[operators.size()])
                    [ThreadLocalRandom.current().nextInt(0, operators.size())];
        }

        protected double generateNumber() {
            return DoubleRounder.GetDoubleWithPrecision(
                    ThreadLocalRandom.current().nextDouble(
                            getMinNumber(), getMaxNumber() + Double.MIN_VALUE),
                    precision);
        }

        private boolean isDivisionTheOnlyOperator() {  // TODO: change
            return operators.contains(MathOperatorType.DIVISION) && operators.size() == 1;
        }
    }

    protected int precision;
    protected final double number1;
    protected final double number2;
    protected final MathOperatorType operatorType;

    AbstractMathTask(double number1, double number2, int precision, MathOperatorType type)
            throws NotHandledEnumElementException, IncorrectTaskConditionsException {
        this.number1 = DoubleRounder.GetDoubleWithPrecision(number1, precision);
        this.number2 = DoubleRounder.GetDoubleWithPrecision(number2, precision);
        setPrecision(precision);
        this.operatorType = type;
        if (!isArithmeticCorrect()) {
            throw new IncorrectTaskConditionsException();
        }
    }

    AbstractMathTask(double number1, double number2, MathOperatorType type)
            throws NotHandledEnumElementException, IncorrectTaskConditionsException {
        this(number1, number2, 0, type);
    }

    public void setPrecision(int precision) throws IllegalArgumentException {
        if (precision < 0) {
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
        if (Objects.equals(getAnswer(), "NOT_ZERO")) {
            return Double.compare(Double.parseDouble(answer), 0) == 0 ? Result.WRONG : Result.OK;
        }
        return Objects.equals(answer, getAnswer()) ? Result.OK : Result.WRONG;
    }
}
