import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FailureMessageUI extends JFrame {
    //error message를 표시하는 UI 클래스
    public FailureMessageUI() {
        setTitle("오류");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();

        JLabel messageLabel = new JLabel("작업이 실패하였습니다.");

        JButton closeButton = new JButton("닫기");
        closeButton.addActionListener(e -> dispose()); // 닫기 버튼 클릭 시 프로그램 종료

        panel.add(messageLabel);
        panel.add(closeButton);

        add(panel);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new FailureMessageUI();
    }
}
