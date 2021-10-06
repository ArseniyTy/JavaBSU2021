package by.ArseniyTY.exceptions;

public class NotEnoughTasksException extends Exception {
    public NotEnoughTasksException() {
        super();
    }

    public NotEnoughTasksException(String message) {
        super(message);
    }
}
