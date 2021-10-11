import by.ArseniyTY.exceptions.IncorrectTaskConditionsException;
import by.ArseniyTY.quizer.QuizesExamples;

public class Main {
    public static void main(String[] args) throws IncorrectTaskConditionsException {
        MainWindow w = new MainWindow();
        var v = QuizesExamples.getQuizMap();
        System.out.println(v.get("IEx").getCorrectAnswerNumber());
    }
}
