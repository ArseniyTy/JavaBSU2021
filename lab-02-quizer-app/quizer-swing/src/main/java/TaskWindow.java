import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TaskWindow {
    private JFrame frame = new JFrame();

    public TaskWindow(MainWindow window) {  // better to pass abstract window, so create it
        int rowsCount = 4;
        int columnsCount = 2;
        frame.setLayout(new GridLayout(rowsCount, columnsCount, 10, 10));

        // KOSTYL. REASON: Can't put elements to certain cells in GridLayout.
        JPanel[][] panelHolder = new JPanel[rowsCount][columnsCount];
        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < columnsCount; j++) {
                panelHolder[i][j] = new JPanel();
                panelHolder[i][j].setLayout(new GridBagLayout());  // GridBag -- allows alignment (default -- center)
                frame.add(panelHolder[i][j]);
                panelHolder[i][j].setBackground(Color.LIGHT_GRAY);  // TO_DELETE
            }
        }

        JLabel taskLabel = new JLabel("Place for task");
        panelHolder[1][0].add(taskLabel);

        JTextField answerTextField = new JTextField("Place for answer");
        panelHolder[2][0].add(answerTextField);


        JLabel questionCountLabel = new JLabel("Question: 3/10");
        JLabel okAnswersLabel = new JLabel("OK: 3");
        JLabel falseAnswersLabel = new JLabel("WRONG: 2");

        var statisticsVerticalBox = Box.createVerticalBox();
        statisticsVerticalBox.add(questionCountLabel);
        statisticsVerticalBox.add(okAnswersLabel);
        statisticsVerticalBox.add(falseAnswersLabel);
        panelHolder[1][1].add(statisticsVerticalBox);


        // Add listener to |answerTextField|, because exactly this component is always active, not the |frame|
        answerTextField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    JOptionPane.showMessageDialog(frame,
                            "OK",
                            "Question result",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        frame.setMinimumSize(new Dimension(400, 500));
        frame.setVisible(true);
    }
}
