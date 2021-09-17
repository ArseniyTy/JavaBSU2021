package by.ArseniyTY.quizer;

import by.ArseniyTY.exceptions.IncorrectTaskConditionsException;
import by.ArseniyTY.exceptions.NotEnoughTasksException;
import by.ArseniyTY.exceptions.NotHandledEnumElementException;
import by.ArseniyTY.exceptions.QuizNotFinishedException;
import by.ArseniyTY.tasks.math_tasks.*;
import by.ArseniyTY.tasks.TextTask;
import by.ArseniyTY.tasks_generators.GroupTaskGenerator;
import by.ArseniyTY.tasks_generators.PoolTaskGenerator;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Program {
    private static Map<String, Quiz> getQuizMap() throws NotHandledEnumElementException, IncorrectTaskConditionsException {
        var quizMap = new HashMap<String, Quiz>();

        var intExpTG1 = new ExpressionMathTask.Generator(-10, 10,
                EnumSet.of(MathOperator.SUM, MathOperator.DIFFERENCE, MathOperator.MULTIPLICATION,
                        MathOperator.DIVISION));
        quizMap.put("IEx", new Quiz(intExpTG1, 2));

        var intEqTG1 = new EquationMathTask.Generator(-10, 10,
                EnumSet.of(MathOperator.SUM, MathOperator.DIFFERENCE, MathOperator.MULTIPLICATION,
                        MathOperator.DIVISION));
        quizMap.put("IEq", new Quiz(intEqTG1, 2));

        var realExpTG1 = new ExpressionMathTask.Generator(-10, 10, 2,
                EnumSet.of(MathOperator.SUM, MathOperator.DIFFERENCE, MathOperator.MULTIPLICATION,
                        MathOperator.DIVISION));
        quizMap.put("REx", new Quiz(realExpTG1, 2));

        var realEqTG1 = new EquationMathTask.Generator(-10, 10, 2,
                EnumSet.of(MathOperator.SUM, MathOperator.DIFFERENCE, MathOperator.MULTIPLICATION,
                        MathOperator.DIVISION));
        quizMap.put("REq", new Quiz(realEqTG1, 2));

        var textT1 = new TextTask("Who is the best programmer ever?", "me");
        var textT2 = new TextTask("Who is the worst programmer ever?", "me");
        var poolTG1 = new PoolTaskGenerator(false, textT1, textT2);
        quizMap.put("PT1", new Quiz(poolTG1, 2));

        quizMap.put("Mix1", new Quiz(
                new GroupTaskGenerator(intExpTG1, realExpTG1, intEqTG1, realEqTG1, poolTG1), 10));


        var intEqTG2 = new EquationMathTask.Generator(0, 1,
                EnumSet.of(MathOperator.DIVISION));
        quizMap.put("IEq2", new Quiz(intEqTG2, 10));

        var poolTG2 = new PoolTaskGenerator(false,
                new EquationMathTask(0, 0, 0, MathOperator.MULTIPLICATION),
                new ExpressionMathTask(2, 0.01, 2, MathOperator.DIVISION),
                new TextTask("Who is who?", "me"));
        quizMap.put("PT2", new Quiz(poolTG2, 3));

        return quizMap;
    }

    private static void CheckErrorTasks() throws NotHandledEnumElementException, RuntimeException {
        try {
            new ExpressionMathTask(1, 0, MathOperator.DIVISION);
            throw new RuntimeException("This task must throw exception");
        } catch (IncorrectTaskConditionsException ignored) {}
        try {
            new ExpressionMathTask(1, 0, MathOperator.DIVISION);
            throw new RuntimeException("This task must throw exception");
        } catch (IncorrectTaskConditionsException ignored) {}
        try {
            new ExpressionMathTask(0, 0, MathOperator.DIVISION);
            throw new RuntimeException("This task must throw exception");
        } catch (IncorrectTaskConditionsException ignored) {}
        try {
            new ExpressionMathTask(1, 0, MathOperator.DIVISION);
            throw new RuntimeException("This task must throw exception");
        } catch (IncorrectTaskConditionsException ignored) {}
        try {
            new EquationMathTask(0, 1, MathOperator.DIVISION);
            throw new RuntimeException("This task must throw exception");
        } catch (IncorrectTaskConditionsException ignored) {}
        try {
            new EquationMathTask(1, 0, MathOperator.DIVISION);
            throw new RuntimeException("This task must throw exception");
        } catch (IncorrectTaskConditionsException ignored) {}
        try {
            new EquationMathTask(0, 1, MathOperator.MULTIPLICATION);
            throw new RuntimeException("This task must throw exception");
        } catch (IncorrectTaskConditionsException ignored) {}
        try {
            new ExpressionMathTask(1.0, 0.0, 2, MathOperator.DIVISION);
            throw new RuntimeException("This task must throw exception");
        } catch (IncorrectTaskConditionsException ignored) {}
        try {
            new ExpressionMathTask(0.0, 0.0, 2, MathOperator.DIVISION);
            throw new RuntimeException("This task must throw exception");
        } catch (IncorrectTaskConditionsException ignored) {}
        try {
            new EquationMathTask(0.00, 1.00, 2, MathOperator.DIVISION);
            throw new RuntimeException("This task must throw exception");
        } catch (IncorrectTaskConditionsException ignored) {}try {
            new EquationMathTask(1.00, 0.00, 2, MathOperator.DIVISION);
            throw new RuntimeException("This task must throw exception");
        } catch (IncorrectTaskConditionsException ignored) {}
        try {
            new EquationMathTask(0.00, 1.00, 2, MathOperator.MULTIPLICATION);
            throw new RuntimeException("This task must throw exception");
        } catch (IncorrectTaskConditionsException ignored) {}
    }

    public static void main(String[] args)
            throws QuizNotFinishedException, NotHandledEnumElementException, NotEnoughTasksException,
            IncorrectTaskConditionsException {
        CheckErrorTasks();

        Scanner scanner = new Scanner(System.in);

        var quizMap = getQuizMap();
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
        while (!test.isFinished()) {  // TODO: hide nextTask(). It should be more encapsulated.
            var task = test.nextTask();
            System.out.println(task.getText());

            System.out.println("Your answer ... ");
            var result = test.provideAnswer(scanner.nextLine());
            while (result == Result.INCORRECT_INPUT) {
                System.out.println("Incorrect input. Enter your answer again ... ");
                result = test.provideAnswer(scanner.nextLine());
            }
            switch (result) {
                case OK -> System.out.println("OK!");
                case WRONG -> System.out.println("WRONG!");
                default -> throw new NotHandledEnumElementException();
            }
        }
        System.out.println("Your mark is " + test.getMark());
    }
}
