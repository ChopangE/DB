import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class UserList extends JFrame {

    private JComboBox<String> stringComboBox;
    private JTextField inputTextField;  // 추가된 부분
    private JButton selectButton,cancelButton;

    private String[] stringArray;
    int uid;

    public UserList(int n) {
        super("가족 목록");
        uid = n;
        stringArray = new Select(uid).all();

        stringComboBox = new JComboBox<>(stringArray);
        inputTextField = new JTextField(30);
        selectButton = new JButton("RSVP보내기");
        cancelButton = new JButton("돌아가기");

        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String guest = (String) stringComboBox.getSelectedItem();
                String r_content = inputTextField.getText();
                //이름을 통해 uid를 찾는 query를 보내고 그 uid를 gid에 저장
                int gid = new Select(uid).nametoUid(guest);
                LocalTime now = LocalTime.now();
                DateTimeFormatter fhour = DateTimeFormatter.ofPattern("HH");
                DateTimeFormatter fmin = DateTimeFormatter.ofPattern("mm");
                int hour = Integer.parseInt(now.format(fhour));
                int min = Integer.parseInt(now.format(fmin));
                //uid(host id), gid, 이벤트 내용, 현재시간, check(0: 초기에는 무응답)정보를 rsvps table에 insert
                new Insert(uid).Rsvp(uid,gid,r_content,hour,min,0);


                JOptionPane.showMessageDialog(UserList.this, "받는 사람: " + guest + "\n내용: " + r_content);
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        JPanel panel = new JPanel();
        panel.add(new JLabel("가족 선택:"));
        panel.add(stringComboBox);

        panel.add(new JLabel("상세한 이벤트 내용:"));
        panel.add(inputTextField);

        panel.add(selectButton);
        panel.add(cancelButton);
        getContentPane().add(panel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {

        new UserList(8);


    }
}
