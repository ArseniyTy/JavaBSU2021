package by.ArseniyTY.tasks_generators.math_tasks_generators;

import by.ArseniyTY.quizer.TaskGenerator;

interface MathTaskGenerator extends TaskGenerator {
    double getMinNumber();
    double getMaxNumber();

    default double getDiffNumber() {
        return getMaxNumber() - getMinNumber();
    }
}
