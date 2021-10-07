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
    private static Map<String, Quiz> getQuizMap() throws IncorrectTaskConditionsException {
        var quizMap = new HashMap<String, Quiz>();

        var intExpTG1 = new ExpressionMathTask.Generator(-10, 10,
                EnumSet.of(MathOperator.SUM, MathOperator.DIFFERENCE, MathOperator.MULTIPLICATION,
                        MathOperator.DIVISION));
        quizMap.put("IEx", new Quiz(intExpTG1, 2));

        var intEqTG1 = new EquationMathTask.Generator(-10, 10,
                EnumSet.of(MathOperator.SUM, MathOperator.DIFFERENCE, MathOperator.MULTIPLICATION,
                        MathOperator.DIVISION));
        quizMap.put("IEq", new Quiz(intEqTG1, 2));

        var realSmallEq = new EquationMathTask.Generator(0, 0.01, 1,
                EnumSet.of(MathOperator.MULTIPLICATION, MathOperator.DIVISION));
        quizMap.put("RSEq", new Quiz(realSmallEq, 4));

        var realSmallEx = new ExpressionMathTask.Generator(0, 0.01, 1,
                EnumSet.of(MathOperator.MULTIPLICATION, MathOperator.DIVISION));
        quizMap.put("RSEx", new Quiz(realSmallEx, 4));

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
}
