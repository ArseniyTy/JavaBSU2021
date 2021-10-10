import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainWindow extends JFrame {
    private JList<String> list;

    public MainWindow() {
        super();

        setLayout(new GridBagLayout());
        // allows in an awful way to use some normal grid layout features
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel label = new JLabel("List of tasks");
        label.setBounds(100, 0, 100,100);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.ipady = 100;
        add(label, gbc);

        DefaultListModel<String> lits_model = new DefaultListModel<>();
        lits_model.addElement("Item1");
        lits_model.addElement("Wrfkaejwkj;c;klsdjfoijaiofjio[ew[jfijasidjl;kasdjfkljasf");
        list = new JList<>(lits_model);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(list, gbc);


        setMinimumSize(new Dimension(400, 500));
//        setLayout(null);  // musthave, if not layout
        setVisible(true);

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
        setVisible(false);
        new TaskWindow(this);
    }
}
