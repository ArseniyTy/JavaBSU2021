package by.ArseniyTY.tasks.math_tasks;

import by.ArseniyTY.exceptions.IncorrectTaskConditionsException;
import by.ArseniyTY.exceptions.NotHandledEnumElementException;
import by.ArseniyTY.quizer.Result;

import java.util.EnumSet;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public abstract class AbstractMathTask implements MathTask {
    public static abstract class Generator implements MathTask.Generator {
        protected int precision;
        private double minNumber;
        private double maxNumber;
        private final EnumSet<MathOperator> operators;

        public Generator(double minNumber, double maxNumber, int precision, EnumSet<MathOperator> operators) {
            setMinAndMaxNumber(minNumber, maxNumber);
            setPrecision(precision);
            this.operators = operators;

            if (Objects.equals(DoubleRounder.GetDoubleStringWithPrecision(maxNumber, precision), "0") &&
                Objects.equals(DoubleRounder.GetDoubleStringWithPrecision(minNumber, precision), "0") &&
                isDivisionTheOnlyOperator()) {
                throw new IllegalArgumentException();
            }
        }

        public Generator(double minNumber, double maxNumber, EnumSet<MathOperator> operators) {
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

        protected MathOperator generateOperator() {
            return operators.toArray(new MathOperator[0])
                    [ThreadLocalRandom.current().nextInt(0, operators.size())];
        }

        protected double generateNumber() {
            return DoubleRounder.GetDoubleWithPrecision(
                    ThreadLocalRandom.current().nextDouble(
                            getMinNumber(), getMaxNumber() + Double.MIN_VALUE),
                    precision);
        }

        private boolean isDivisionTheOnlyOperator() {
            return Objects.equals(operators, EnumSet.of(MathOperator.DIVISION));
        }
    }

    protected int precision;
    protected final double number1;
    protected final double number2;
    protected final MathOperator operatorType;

    public AbstractMathTask(double number1, double number2, int precision, MathOperator type)
            throws IncorrectTaskConditionsException {
        this.number1 = DoubleRounder.GetDoubleWithPrecision(number1, precision);
        this.number2 = DoubleRounder.GetDoubleWithPrecision(number2, precision);
        setPrecision(precision);
        this.operatorType = type;
        if (!isArithmeticCorrect()) {
            throw new IncorrectTaskConditionsException();
        }
    }

    public AbstractMathTask(double number1, double number2, MathOperator type) throws IncorrectTaskConditionsException {
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
    public Result validate(String answer) {
        double num;
        try {
            num = Double.parseDouble(answer);
        } catch (NumberFormatException ex){
            return Result.INCORRECT_INPUT;
        }
        if (!Objects.equals(answer, DoubleRounder.GetDoubleStringWithPrecision(num, precision))) {
            return Result.INCORRECT_INPUT;
        }

        if (Objects.equals(getAnswer(), "ANY")) {
            return Result.OK;
        }
        if (Objects.equals(getAnswer(), "NOT_ZERO")) {
            return Double.compare(num, 0) == 0 ? Result.WRONG : Result.OK;
        }
        return Objects.equals(answer, getAnswer()) ? Result.OK : Result.WRONG;
    }
}
