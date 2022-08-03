import by.ArseniyTY.quizer.QuizesExamples;
import by.ArseniyTY.quizer.Result;
import by.ArseniyTY.exceptions.IncorrectTaskConditionsException;
import by.ArseniyTY.exceptions.NotEnoughTasksException;
import by.ArseniyTY.exceptions.NotHandledEnumElementException;
import by.ArseniyTY.exceptions.QuizNotFinishedException;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws QuizNotFinishedException, NotHandledEnumElementException,
            NotEnoughTasksException, IncorrectTaskConditionsException {

        Scanner scanner = new Scanner(System.in);

        var quizMap = QuizesExamples.getQuizMap();
        System.out.println("List of available tests:");
        for (var testName : quizMap.keySet()) {
            System.out.println(testName);
        }

        System.out.println("Enter test name ...");
        var test_name = scanner.nextLine();
        while (!quizMap.containsKey(test_name)) {
            System.out.println("No such test name. Enter test name again ...");
            test_name = scanner.nextLine();
        }

        var test = quizMap.get(test_name);
        while (!test.isFinished()) {
            var task = test.nextTask();
            System.out.println(task.getText());

            System.out.println("Your answer ... ");
            var result = test.provideAnswer(scanner.nextLine());
            while (result == Result.INCORRECT_INPUT) {
                System.out.println("Incorrect input. Enter your answer again ... ");
                result = test.provideAnswer(scanner.nextLine());
            }
            switch (result) {
                case OK, WRONG -> System.out.println(result + "!");
                default -> throw new NotHandledEnumElementException();
            }
        }
        System.out.println("Your mark is " + test.getMark());
    }
}
