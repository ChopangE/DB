import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Date;


public class SignUp {
    public void createSignUpUI() {
        JFrame frame = new JFrame("회원가입");
        frame.setSize(400, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(6, 4));

        JLabel nameLabel = new JLabel("이름:");
        JTextField nameField = new JTextField(20);

        JLabel positionLabel = new JLabel("가족 내 역할:");
        JTextField positionField = new JTextField(20);


        JLabel IDLabel = new JLabel("아이디:");
        JTextField IDField = new JTextField(20);

        JLabel passwordLabel = new JLabel("비밀번호:");
        JPasswordField passwordField = new JPasswordField(20);


        JButton signUpButton = new JButton("가입하기");
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
                //여기서 DB랑 연결하고 users table에 insert

                new Insert("users", tmp, 4 ).Start();


                JOptionPane.showMessageDialog(frame, "가입이 완료되었습니다!");
                frame.setVisible(false);
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

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SignUp signUp = new SignUp();
        signUp.createSignUpUI();
    }
}
