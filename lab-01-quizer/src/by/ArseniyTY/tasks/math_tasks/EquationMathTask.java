package by.ArseniyTY.tasks.math_tasks;

import by.ArseniyTY.exceptions.NotHandledEnumElementException;

abstract class EquationMathTask extends AbstractMathTask {
    EquationMathTask(MathOperatorType type) {
        super(type);
    }

    @Override
    public String getText() throws NotHandledEnumElementException {
        return getNumber1() + getOperatorStringRepresentation() + "x=" + getNumber2();
    }
}
