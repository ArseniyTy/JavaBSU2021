import by.ArseniyTY.exceptions.NotEnoughTasksException;
import by.ArseniyTY.exceptions.NotHandledEnumElementException;
import by.ArseniyTY.exceptions.QuizNotFinishedException;
import by.ArseniyTY.quizer.Quiz;
import by.ArseniyTY.quizer.Result;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TaskWindow extends JFrame {

    private final Quiz quiz;

    private final JFrame parentWindow;
    private final JLabel taskLabel = new JLabel();
    private final JTextField answerTextField = new JTextField();
    private final JLabel okAnswersLabel = new JLabel();
    private final JLabel falseAnswersLabel = new JLabel();

    public TaskWindow(JFrame parentWindow, Quiz quiz) throws QuizNotFinishedException, NotEnoughTasksException {
        this.parentWindow = parentWindow;
        this.quiz = quiz;

        setUI();
        setAnswerTextFieldListener();
        updateWindow();

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                parentWindow.setVisible(true);
            }
        });
        setMinimumSize(parentWindow.getMinimumSize());
        setVisible(true);
    }

    private void setUI() {
        int rowsCount = 4;
        int columnsCount = 2;
        setLayout(new GridLayout(rowsCount, columnsCount, 10, 10));

        // KOSTYL. REASON: Can't put elements to certain cells in GridLayout.
        JPanel[][] panelHolder = new JPanel[rowsCount][columnsCount];
        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < columnsCount; j++) {
                panelHolder[i][j] = new JPanel();
                panelHolder[i][j].setLayout(new GridBagLayout());  // GridBag -- allows alignment (default -- center)
                add(panelHolder[i][j]);
//                panelHolder[i][j].setBackground(Color.LIGHT_GRAY);  // Comfortable to develop with it
            }
        }

        panelHolder[1][0].add(taskLabel);

        answerTextField.setPreferredSize(new Dimension(100, 20));  // Otherwise, is not shrinked in GridBagLayout
        panelHolder[2][0].add(answerTextField);

        var statisticsVerticalBox = Box.createVerticalBox();
        statisticsVerticalBox.add(okAnswersLabel);
        statisticsVerticalBox.add(falseAnswersLabel);
        panelHolder[1][1].add(statisticsVerticalBox);
    }

    // Add listener to |answerTextField|, because exactly this component is always active, not the |frame|
    private void setAnswerTextFieldListener() {
        var self = this;  // to pass it below
        answerTextField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    Result result;
                    try {
                        result = quiz.provideAnswer(answerTextField.getText());
                    } catch (NotHandledEnumElementException ex) {
                        ex.printStackTrace();
                        System.exit(1);
                        return;
                    }

                    String dialogMessage = switch (result) {
                        case OK, WRONG -> result + "!";
                        case INCORRECT_INPUT -> "Incorrect input. Enter your answer again ... ";
                    };
                    JOptionPane.showMessageDialog(self,
                            dialogMessage,
                            "Question result",
                            JOptionPane.INFORMATION_MESSAGE);

                    try {
                        updateWindow();
                    } catch (NotEnoughTasksException | QuizNotFinishedException ex) {
                        ex.printStackTrace();
                        System.exit(1);
                    }
                }
            }
        });
    }

    void updateWindow() throws NotEnoughTasksException, QuizNotFinishedException {
        if (quiz.isFinished()) {
            JOptionPane.showMessageDialog(this,
                    String.format("""
                            Your mark is: %f\s
                            OK answers: %d\s
                            WRONG answers: %d\s
                            Incorrect inputs: %d""",
                            quiz.getMark(), quiz.getCorrectAnswerNumber(), quiz.getWrongAnswerNumber(),
                            quiz.getIncorrectInputNumber()),
                    "Quiz result",
                    JOptionPane.INFORMATION_MESSAGE);
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            return;
        }
        var task = quiz.nextTask();
        taskLabel.setText(String.format("<html>TASK:<br> %s</html>", task.getText()));
        answerTextField.setText("");
        okAnswersLabel.setText(String.format("<html>OK answers: %s</html>", quiz.getCorrectAnswerNumber()));
        falseAnswersLabel.setText(String.format("<html>WRONG answers: %s</html>", quiz.getWrongAnswerNumber()));
    }
}
