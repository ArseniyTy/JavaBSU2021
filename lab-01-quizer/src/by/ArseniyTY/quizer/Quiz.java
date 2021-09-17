package by.ArseniyTY.quizer;

import by.ArseniyTY.exceptions.NotEnoughTasksException;
import by.ArseniyTY.exceptions.NotHandledEnumElementException;
import by.ArseniyTY.exceptions.QuizNotFinishedException;

class Quiz {
    private final Task.Generator generator;
    private Task current_task;
    private boolean isCurrentTaskFinished = true;
    private int taskCount;
    private int correctAnswerNumber = 0;
    private int wrongAnswerNumber = 0;
    private int incorrectInputNumber = 0;

    Quiz(Task.Generator generator, int taskCount) {
        this.generator = generator;
        setTaskCount(taskCount);
    }

    private void setTaskCount(int taskCount) {
        if (taskCount <= 0) {
            throw new IllegalArgumentException();
        }
        this.taskCount = taskCount;
    }

      Task nextTask() throws NotHandledEnumElementException, NotEnoughTasksException {
        if (isCurrentTaskFinished) {
            current_task = generator.generate();
            isCurrentTaskFinished = false;
        }
        return current_task;
    }

    Result provideAnswer(String answer) throws NotHandledEnumElementException {
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
        }
        return result;
    }

    boolean isFinished() {
        return correctAnswerNumber + wrongAnswerNumber == taskCount;
    }

    private int getCorrectAnswerNumber() {
        return correctAnswerNumber;
    }

    private int getWrongAnswerNumber() {
        return wrongAnswerNumber;
    }

    private int getIncorrectInputNumber() {
        return incorrectInputNumber;
    }

    double getMark() throws QuizNotFinishedException {
        if (!isFinished()) {
            throw new QuizNotFinishedException();
        }
        return (double) correctAnswerNumber / taskCount;
    }
}
