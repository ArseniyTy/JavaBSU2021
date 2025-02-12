package by.ArseniyTY.quizer;

import by.ArseniyTY.exceptions.NotEnoughTasksException;
import by.ArseniyTY.exceptions.NotHandledEnumElementException;
import by.ArseniyTY.exceptions.QuizNotFinishedException;

public class Quiz {
    private final Task.Generator generator;
    private Task current_task;
    private boolean isCurrentTaskFinished = true;
    private int taskCount;
    private int correctAnswerNumber = 0;
    private int wrongAnswerNumber = 0;
    private int incorrectInputNumber = 0;

    public Quiz(Task.Generator generator, int taskCount) throws IllegalArgumentException {
        this.generator = generator;
        setTaskCount(taskCount);
    }

    private void setTaskCount(int taskCount) throws IllegalArgumentException {
        if (taskCount <= 0) {
            throw new IllegalArgumentException();
        }
        this.taskCount = taskCount;
    }

    public Task nextTask() throws NotEnoughTasksException {
        if (isCurrentTaskFinished) {
            current_task = generator.generate();
            isCurrentTaskFinished = false;
        }
        return current_task;
    }

    public Result provideAnswer(String answer) throws NotHandledEnumElementException {
        var result = current_task.validate(answer);
        switch (result) {
            case OK -> {
                correctAnswerNumber++;
                isCurrentTaskFinished = true;
            }
            case WRONG -> {
                wrongAnswerNumber++;
                isCurrentTaskFinished = true;
            }
            case INCORRECT_INPUT -> incorrectInputNumber++;
            default -> throw new NotHandledEnumElementException();
        }
        return result;
    }

    public boolean isFinished() {
        return correctAnswerNumber + wrongAnswerNumber == taskCount;
    }

    public int getCorrectAnswerNumber() {
        return correctAnswerNumber;
    }

    public int getWrongAnswerNumber() {
        return wrongAnswerNumber;
    }

    public int getIncorrectInputNumber() {
        return incorrectInputNumber;
    }

    public double getMark() throws QuizNotFinishedException {
        if (!isFinished()) {
            throw new QuizNotFinishedException();
        }
        return (double) correctAnswerNumber / taskCount;
    }
}
