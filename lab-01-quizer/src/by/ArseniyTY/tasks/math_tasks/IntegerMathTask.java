package by.ArseniyTY.tasks.math_tasks;

import by.ArseniyTY.exceptions.NotHandledEnumElementException;
import by.ArseniyTY.quizer.Result;

interface IntegerMathTask extends MathTask {
    default Result validate(String answer) throws NotHandledEnumElementException {
        try {
            Integer.parseInt(answer);
        } catch (NumberFormatException ex){
            return Result.INCORRECT_INPUT;
        }
        return Result.OK;
    }
}
