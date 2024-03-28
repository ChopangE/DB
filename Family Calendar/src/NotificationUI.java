import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NotificationUI extends  JFrame{
    private JFrame frame;

    public NotificationUI() {
        frame = new JFrame("알림 메시지");
        createUI();
    }

    private void createUI() {
        JPanel panel = new JPanel();

        // 알림 버튼
        JButton showNotificationButton = new JButton("일정 확인");

        showNotificationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 알림 내용
                String notificationMessage = "이벤트 알림.\n";
                notificationMessage += "곧 있을 일정 ";

                // 알림 표시
                JOptionPane.showMessageDialog(frame, notificationMessage, "알림", JOptionPane.INFORMATION_MESSAGE);
                frame.setVisible(false);
            }
        });

        panel.add(showNotificationButton);

        frame.getContentPane().add(panel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                NotificationUI notificationUI = new NotificationUI();
            }
        });
    }
}
