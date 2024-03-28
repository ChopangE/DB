import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrintUserList extends JFrame {

    private JComboBox<String> stringComboBox;
    private JButton selectButton;
    private JTextField outPut;
    private String[] stringArray;
    int uid;
    public PrintUserList(int n) {
        super("가족 목록");
        uid = n;
        //단순히 모든 user의 name을 가지고 온다.
        stringArray = new Select(uid).all();
        stringComboBox = new JComboBox<>(stringArray);
        selectButton = new JButton("확인");

        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JPanel panel = new JPanel();
        panel.add(stringComboBox);
        panel.add(selectButton);

        getContentPane().add(panel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {

                new PrintUserList(0);

    }
}
