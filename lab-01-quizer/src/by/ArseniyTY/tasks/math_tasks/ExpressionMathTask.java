package by.ArseniyTY.tasks.math_tasks;

import by.ArseniyTY.exceptions.NotHandledEnumElementException;

abstract class ExpressionMathTask extends AbstractMathTask {
    ExpressionMathTask(MathOperatorType type) {
        super(type);
    }

    @Override
    public String getText() throws NotHandledEnumElementException {
        return getNumber1() + getOperatorStringRepresentation() + getNumber2() + "=?";
    }
}
