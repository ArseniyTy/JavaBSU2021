package by.ArseniyTY.tasks_generators.math_tasks_generators;

import by.ArseniyTY.exceptions.IncorrectTaskConditionsException;
import by.ArseniyTY.exceptions.NotHandledEnumElementException;
import by.ArseniyTY.quizer.Task;
import by.ArseniyTY.tasks.math_tasks.EquationMathTask;
import by.ArseniyTY.tasks.math_tasks.MathOperatorType;

import java.util.EnumSet;

public class EquationMathTaskGenerator extends AbstractMathTaskGenerator {
    public EquationMathTaskGenerator(int precision, double minNumber, double maxNumber,
                                       EnumSet<MathOperatorType> operators) {
        super(precision, minNumber, maxNumber, operators);
    }

    @Override
    public Task generate() throws NotHandledEnumElementException {
        EquationMathTask task;
        do {
            try {
                task = new EquationMathTask(precision, generateNumber(), generateNumber(), generateOperator());
            } catch (IncorrectTaskConditionsException ignored) {
                task = null;
            }
        } while (task == null);
        return task;
    }
}
