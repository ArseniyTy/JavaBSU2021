package by.ArseniyTY.tasks.math_tasks;

public enum MathOperatorType {
    SUM("+"),
    DIFFERENCE("-"),
    MULTIPLICATION("*"),
    DIVISION("/");

    private final String stringRepresentation;

    MathOperatorType(String stringRepresentation) {
        this.stringRepresentation = stringRepresentation;
    }

    @Override
    public String toString() {
        return stringRepresentation;
    }
}
