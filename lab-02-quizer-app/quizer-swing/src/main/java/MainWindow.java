import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainWindow {
    private JFrame frame = new JFrame();
    private JList<String> list;
//    private int rowsCount = 3;
//    private int columnsCount = 3;
//    private JPanel[][] panelHolder = new JPanel[rowsCount][columnsCount];

    public MainWindow() {
//        frame.setLayout(new GridLayout(rowsCount, columnsCount, 1, 1));

//        for (int i = 0; i < rowsCount; i++) {
//            for (int j = 0; j < columnsCount; j++) {
//                panelHolder[i][j] = new JPanel();
//                frame.add(panelHolder[i][j]);
//                panelHolder[i][j].setBackground(Color.LIGHT_GRAY);  // TO_DELETE
//            }
//        }

        JLabel label = new JLabel("List of tasks");
        label.setBounds(100, 0, 100,100);
        frame.add(label);
//        panelHolder[0][1].add(label);


        DefaultListModel<String> lits_model = new DefaultListModel<>();
        lits_model.addElement("Item1");
        lits_model.addElement("Item2");
        list = new JList<>(lits_model);
        list.setBounds(100,100, 200,200);
        frame.add(list);
//        panelHolder[1][1].add(list);


        frame.setMinimumSize(new Dimension(400, 500));
        frame.setLayout(null);  // musthave !!!
        frame.setVisible(true);

        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                label.setLocation((frame.getWidth() - label.getWidth()) / 2,
                                label.getHeight() / 4);
                list.setLocation((frame.getWidth() - list.getWidth()) / 2,
                                (frame.getHeight() - list.getHeight()) / 2);
            }
        });

        addListActionListener();
    }

    public void addListActionListener() {
        ActionListener listAL = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = list.getSelectedValue();
                System.out.println(selectedItem);
                openTaskWindow();
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

    private void openTaskWindow() {
        frame.setVisible(false);
        new TaskWindow(this);
    }
}
