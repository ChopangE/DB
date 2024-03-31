import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InvitationUI extends JFrame {
    private JSpinner inviteCountSpinner;
    private JTextField[] nameTextFields;
    private JTextField eventIdTextField;

    //private String year,month, day,hour,min, location, eventname,timeInterval, timeFrame,duration;
    private String []arr = new String[11];

    CalendarMain cal;
    //private int uid;
    public InvitationUI(){
        this("a","a","a","a","a","a","a","a","a","a",1,new CalendarMain(0));
    }
    public InvitationUI(String y, String m, String d, String h, String mi, String lo, String name, String tI, String tF, String du, int id, CalendarMain me) {
        super("초대하기");
        arr[0] = y;
        arr[1] = m;
        arr[2] = d;
        arr[3] = h;
        arr[4] = mi;
        arr[5] = lo;
        arr[6] = name;
        arr[7] = du;
        arr[8] = tI;
        arr[9] = tF;
        arr[10] = Integer.toString(id);
        cal = me;
        createUI();
    }

    private void createUI() {
        JPanel panel = new JPanel();

        JLabel inviteCountLabel = new JLabel("초대할 인원 수:");
        inviteCountSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));

        JLabel nameLabel = new JLabel("이름 입력:");
        nameTextFields = new JTextField[10]; // 최대 10명까지 초대 가능하도록 배열 생성


        // 초대하기 버튼
        JButton inviteButton = new JButton("초대하기");

        // 초대할 인원 수를 선택하는 패널
        JPanel inviteCountPanel = new JPanel();
        inviteCountPanel.add(inviteCountLabel);
        inviteCountPanel.add(inviteCountSpinner);

        // 초대할 인원의 이름을 입력받는 패널
        JPanel nameInputPanel = new JPanel();
        nameInputPanel.setLayout(new BoxLayout(nameInputPanel, BoxLayout.Y_AXIS));
        nameInputPanel.add(nameLabel);
        for (int i = 0; i < 10; i++) {
            nameTextFields[i] = new JTextField(20);
            nameInputPanel.add(nameTextFields[i]);
        }

        JPanel eventIdPanel = new JPanel();

        inviteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int inviteCount = (int) inviteCountSpinner.getValue();
                StringBuilder inviteMessage = new StringBuilder("초대한 인원: " + inviteCount + "명\n");
                //여기서 DB에 insert
                String arr2[] = new String[inviteCount];
                for (int i = 0; i < inviteCount; i++) {
                    String name = nameTextFields[i].getText();
                    if (!name.isEmpty()) {
                        //arr2배열에 name을 입력받는다.
                        arr2[i] = name;
                        inviteMessage.append("이름 ").append(i + 1).append(": ").append(name).append("\n");
                    }
                }
                //초대받을 유저들의 목록(arr2), event세부내역 배열(arr)를 넘겨주어 insert query를 진행한다.
                new Insert("events", arr,11, arr2, inviteCount).Start();

                JOptionPane.showMessageDialog(InvitationUI.this, inviteMessage.toString(), "초대 정보", JOptionPane.INFORMATION_MESSAGE);
                //calendar upate한다.
                cal.Update();
                dispose();
            }
        });

        panel.add(inviteCountPanel);
        panel.add(nameInputPanel);
        panel.add(eventIdPanel);
        panel.add(inviteButton);

        getContentPane().add(panel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                InvitationUI invitationUI = new InvitationUI();
            }
        });
    }
}
