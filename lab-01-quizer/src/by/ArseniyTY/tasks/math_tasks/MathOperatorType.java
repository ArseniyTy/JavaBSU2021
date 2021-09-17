package by.ArseniyTY.tasks.math_tasks;

public enum MathOperatorType {
    SUM("+") {
        @Override
        public double getResult(double number1, double number2) {
            return number1 + number2;
        }
    },
    DIFFERENCE("-") {
        @Override
        public double getResult(double number1, double number2) {
            return number1 - number2;
        }
    },
    MULTIPLICATION("*") {
        @Override
        public double getResult(double number1, double number2) {
            return number1 * number2;
        }
    },
    DIVISION("/") {
        @Override
        public double getResult(double number1, double number2) {
            return number1 / number2;
        }
    };

    private final String stringRepresentation;

    MathOperatorType(String stringRepresentation) {
        this.stringRepresentation = stringRepresentation;
    }

    @Override
    public String toString() {
        return stringRepresentation;
    }

    public abstract double getResult(double number1, double number2);
}
