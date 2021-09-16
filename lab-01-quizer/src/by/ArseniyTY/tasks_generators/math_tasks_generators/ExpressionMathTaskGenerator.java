package by.ArseniyTY.tasks_generators.math_tasks_generators;

import by.ArseniyTY.tasks.math_tasks.MathOperatorType;

import java.util.EnumSet;

abstract class ExpressionMathTaskGenerator extends AbstractMathTaskGenerator {
    ExpressionMathTaskGenerator(EnumSet<MathOperatorType> operators) {
        super(operators);
    }
}
