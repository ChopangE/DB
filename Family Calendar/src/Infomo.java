import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Date;


public class Infomo {
    public void Infomodify(int uid) {

        String[] res;
        //uid에 해당하는 user 정보를 users table에서 가지고옴.
        res = new Select(uid).use();

        JFrame frame = new JFrame("회원 정보 수정");
        frame.setSize(400, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(6, 4));

        JLabel nameLabel = new JLabel("현재 이름:" + res[2] + "  ->");
        JTextField nameField = new JTextField(20);

        JLabel positionLabel = new JLabel("현재 역할:"+ res[3] + "  ->");
        JTextField positionField = new JTextField(20);

        JLabel IDLabel = new JLabel("현재 아이디:"+ res[0] + "  ->");
        JTextField IDField = new JTextField(20);

        JLabel passwordLabel = new JLabel("현재 비밀번호:" + res[1] + "  ->");
        JPasswordField passwordField = new JPasswordField(20);


        JButton signUpButton = new JButton("수정하기");
        JButton backButton = new JButton("돌아가기");
        signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String ID = IDField.getText();
                String position = positionField.getText();
                String name = nameField.getText();
                String password = passwordField.getText();

                String[] tmp = new String[4];
                tmp[0] = ID;
                tmp[1] = password;
                tmp[2] = name;
                tmp[3] = position;
                //여기서 DB랑 연결하고 Update
                new Update().Use(tmp,uid);


                JOptionPane.showMessageDialog(frame, "수정이 완료되었습니다!");
                frame.setVisible(false);
            }
        });
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        frame.add(IDLabel);
        frame.add(IDField);
        frame.add(passwordLabel);
        frame.add(passwordField);
        frame.add(nameLabel);
        frame.add(nameField);
        frame.add(positionLabel);
        frame.add(positionField);

        frame.add(signUpButton);
        frame.add(backButton);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Infomo in = new Infomo();
        in.Infomodify(8);
    }
}
