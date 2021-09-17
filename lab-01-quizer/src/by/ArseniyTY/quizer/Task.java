package by.ArseniyTY.quizer;

import by.ArseniyTY.exceptions.NotEnoughTasksException;

public interface Task {
    interface Generator {
        Task generate() throws NotEnoughTasksException;
    }

    String getText();
    Result validate(String answer);
}
