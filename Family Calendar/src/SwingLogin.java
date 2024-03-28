import java.awt.*;

import java.awt.event.*;

import javax.swing.*;


public class SwingLogin extends JFrame{

    private JPanel loginPanel = new JPanel(new GridLayout(3, 2));

    private JLabel idLabel = new JLabel("아이디 ");

    private JLabel pwLabel = new JLabel("비밀번호 ");

    private JTextField idText = new JTextField();

    private JPasswordField pwText = new JPasswordField();

    private JButton loginBtn = new JButton("로그인");

    private JButton singUp = new JButton("회원가입");


    public SwingLogin() {

        super("로그인 창");


        this.setContentPane(loginPanel);

        loginPanel.add(idLabel);

        loginPanel.add(pwLabel);

        loginPanel.add(idText);

        loginPanel.add(pwText);

        loginPanel.add(singUp);

        loginPanel.add(loginBtn);




//라벨 가운데 정렬

        idLabel.setHorizontalAlignment(NORMAL);

        pwLabel.setHorizontalAlignment(NORMAL);





        setSize(350,150);

        this.setLocationRelativeTo(null);


        this.setVisible(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);




//로그인 버튼을 눌렀을때

        loginBtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {



//아이디 비번 확인 테스트 코드

                String id = idText.getText().trim();

                String pw = pwText.getText().trim();



                //입력받지 못한 경우
                if(id.length()==0 || pw.length()==0) {

                    JOptionPane.showMessageDialog(null, "아이디 또는 비밀번호를 입력 하셔야 됩니다.", "아이디나 비번을 입력!", JOptionPane.DEFAULT_OPTION);

                    return;

                }

                String[] arr = new String[2];
                arr[0] = id;
                arr[1] = pw;
                //id와 pw로 query를 날려 해당 id pw를 가지고 있는 유저의 uid를 가지고 온다.
                int check = new Select("users",arr,2).Start();

                if(check > 0){ //로그인 성공
                    JOptionPane.showMessageDialog(null, "로그인 성공", "로그인 확인", JOptionPane.DEFAULT_OPTION);
                    dispose();
                    new CalendarMain(check);    //해당 uid로 calendarMain을 연다.
                }
                else{
                    //로그인 실패
                    JOptionPane.showMessageDialog(null, "로그인 실패", "로그인 확인", JOptionPane.DEFAULT_OPTION);
                }







            }

        });




//회원가입 버튼을 눌렀을때

        singUp.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                SignUp signUp = new SignUp();
                //회원가입 UI가 표시된다.
                signUp.createSignUpUI();
            }

        });


    }


    public static void main(String[] args) {
        //calendar에서 맨 처음 실행되는 코드
        new SwingLogin();
    }

}