import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class UserNameListUI extends JFrame {

    private DefaultListModel<String> nameListModel;
    private JList<String> nameList;

    public UserNameListUI(ArrayList<String> arr) {
        super("그 시간대 가능한 유저 리스트");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);

        nameListModel = new DefaultListModel<>();
        nameListModel.addElement("초대 가능한 인원 명단 : ");
        if (arr != null) {
            for (int i = 0; i < arr.size(); i++) {
                nameListModel.addElement(arr.get(i));
            }
        }

        nameList = new JList<>(nameListModel);

        JScrollPane scrollPane = new JScrollPane(nameList);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);

        // 확인 버튼 추가
        JButton confirmButton = new JButton("확인");
        confirmButton.addActionListener(e -> {
            dispose();
        });
        panel.add(confirmButton, BorderLayout.SOUTH);

        getContentPane().add(panel);

        setVisible(true);
    }

    public static void main(String[] args) {


                new UserNameListUI(new ArrayList<>());


    }
}
