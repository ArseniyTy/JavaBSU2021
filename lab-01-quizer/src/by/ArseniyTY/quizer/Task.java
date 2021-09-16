package by.ArseniyTY.quizer;

import by.ArseniyTY.exceptions.NotHandledEnumElementException;

public interface Task {
    String getText() throws NotHandledEnumElementException;
    Result validate(String answer) throws NotHandledEnumElementException;
}
