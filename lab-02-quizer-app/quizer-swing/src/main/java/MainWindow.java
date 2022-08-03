import by.ArseniyTY.exceptions.IncorrectTaskConditionsException;
import by.ArseniyTY.exceptions.NotEnoughTasksException;
import by.ArseniyTY.exceptions.QuizNotFinishedException;
import by.ArseniyTY.quizer.Quiz;
import by.ArseniyTY.quizer.QuizesExamples;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainWindow extends JFrame {

    private final JLabel label = new JLabel("List of tasks");
    private JList<String> list;

    public MainWindow() {
        try {
            setUI();
        } catch (IncorrectTaskConditionsException e) {
            e.printStackTrace();
            System.exit(1);
        }
        setMinimumSize(new Dimension(400, 500));
        setDefaultCloseOperation(EXIT_ON_CLOSE);  // Otherwise, the program will be still running
        setVisible(true);
    }

    private void setUI() throws IncorrectTaskConditionsException {
//        setLayout(null);  // musthave, if no layout
        setLayout(new GridBagLayout());

        // allows in an awful way to use some normal grid layout features
        GridBagConstraints gbc = new GridBagConstraints();

        // label setup
        label.setBounds(100, 0, 100,100);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.ipady = 100;
        add(label, gbc);

        // list setup
        var listModel = new DefaultListModel<String>();
        for (var quizName : QuizesExamples.getQuizMap().keySet()) {
            listModel.addElement(quizName);
        }
        list = new JList<>(listModel);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(list, gbc);
        setListActionListener();
    }

    private void setListActionListener() {
        ActionListener listAL = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    openTaskWindow(QuizesExamples.getQuizMap().get(list.getSelectedValue()));
                } catch (IncorrectTaskConditionsException | QuizNotFinishedException | NotEnoughTasksException ex) {
                    ex.printStackTrace();
                    System.exit(1);
                }
            }
        };

        list.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    listAL.actionPerformed(new ActionEvent(e.getSource(), e.getID(), "ENTER"));
                }
            }
        });
        list.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    listAL.actionPerformed(new ActionEvent(e.getSource(), e.getID(), "ENTER"));
                }
            }
        });

    }

    private void openTaskWindow(Quiz quiz) throws QuizNotFinishedException, NotEnoughTasksException {
        setVisible(false);
        new TaskWindow(this, quiz);
    }
}