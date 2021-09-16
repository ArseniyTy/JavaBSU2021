package by.ArseniyTY.tasks_generators.math_tasks_generators;

import by.ArseniyTY.tasks.math_tasks.MathOperatorType;

import java.util.EnumSet;
import java.util.concurrent.ThreadLocalRandom;

abstract class AbstractMathTaskGenerator implements MathTaskGenerator {
    private final EnumSet<MathOperatorType> operators;

    AbstractMathTaskGenerator(EnumSet<MathOperatorType> operators) {
        this.operators = operators;
    }

    protected MathOperatorType generateOperator() {
        return operators.toArray(new MathOperatorType[operators.size()])
                [ThreadLocalRandom.current().nextInt(0, operators.size())];
    }

    protected boolean isDivisionTheOnlyOperator() {
        return operators.contains(MathOperatorType.DIVISION) && operators.size() == 1;
    }
}
