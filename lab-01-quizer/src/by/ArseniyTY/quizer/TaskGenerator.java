package by.ArseniyTY.quizer;

import by.ArseniyTY.exceptions.NotEnoughTasksException;
import by.ArseniyTY.exceptions.NotHandledEnumElementException;

public interface TaskGenerator {
    Task generate() throws NotHandledEnumElementException, NotEnoughTasksException;
}
