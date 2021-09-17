package by.ArseniyTY.quizer;

import by.ArseniyTY.exceptions.NotEnoughTasksException;

public interface Task {
    String getText();
    Result validate(String answer);

    interface Generator {
        Task generate() throws NotEnoughTasksException;
    }
}
