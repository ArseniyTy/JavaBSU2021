package by.ArseniyTY.tasks.math_tasks;

import by.ArseniyTY.exceptions.NotHandledEnumElementException;
import by.ArseniyTY.quizer.Result;

interface RealMathTask extends MathTask {
    int getPrecision();

    default void setPrecision(int precision) throws IllegalArgumentException {
        if (precision <= 0) {
            throw new IllegalArgumentException();
        }
    }

    default Result validate(String answer) throws NotHandledEnumElementException {
        double num;
        try {
            num = Double.parseDouble(answer);
        } catch (NumberFormatException ex){
            return Result.INCORRECT_INPUT;
        }
        if (num != DoubleRounder.GetDoubleWithPrecision(num, getPrecision())) {
            return Result.INCORRECT_INPUT;
        }
        return Result.OK;
    }
}
