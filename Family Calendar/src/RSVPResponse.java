import javax.lang.model.type.ArrayType;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RSVPResponse extends JFrame {
    private DefaultListModel<String> scheduleListModel;
    private JList<String> scheduleList;
    private JTextField scheduleTextField;
    int uid;

    ArrayList<String> str = new ArrayList<String>();
    public RSVPResponse(){
        this(0);
    }
    public RSVPResponse(int id) {
        super("RSVP 수신함");
        setLayout(new BorderLayout());
        uid = id;
        scheduleListModel = new DefaultListModel<>();
        scheduleList = new JList<>(scheduleListModel);


        JScrollPane scrollPane = new JScrollPane(scheduleList);
        add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new FlowLayout());


        JButton acceptButton = new JButton("수락");
        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                acceptSchedule();
            }
        });
        inputPanel.add(acceptButton);

        JButton rejectButton = new JButton("거절");
        rejectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rejectSchedule();
            }
        });
        inputPanel.add(rejectButton);

        JButton OKButton = new JButton("확인");
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        inputPanel.add(OKButton);

        add(inputPanel, BorderLayout.SOUTH);
        //여기서 수신된 rsvp를 query해 오는데, 10분이 지나지 않은 query만을 가져와서 표시함.(10분 지난 것은 자동으로 거절 처리)
        str = new Select(uid).responseRsvp(uid);
        for(int i = 0; i < str.size(); i = i+2){
            scheduleListModel.addElement("송신자 : " + str.get(i) +", 이벤트 내용 : "+ str.get(i+1));
        }





        setSize(700, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }


    private void acceptSchedule() {
        int selectedIndex = scheduleList.getSelectedIndex();
        if (selectedIndex != -1) {
            //name으로 uid를 찾고 그것을 host id로 저장
            int hid = new Select(uid).nametoUid(str.get(2*selectedIndex));
            //r_content를 rr로 저장
            String rr = str.get(2*selectedIndex+1);
            //hid, r_content, uid(gid)에 대응하는 rsvp를 찾고 check를 1(수락함)으로 설정.
            new Update().RsvpUp(hid, rr, uid, 1);
            JOptionPane.showMessageDialog(this, "수락메시지를 보냈습니다.");

        } else {
            JOptionPane.showMessageDialog(this, "메시지를 선택하세요");
        }
    }

    private void rejectSchedule() {
        int selectedIndex = scheduleList.getSelectedIndex();
        if (selectedIndex != -1) {
            //수락과 동일하고 check만 2(거절함)로 설정
            int hid = new Select(uid).nametoUid(str.get(2*selectedIndex));
            String rr = str.get(2*selectedIndex+1);
            new Update().RsvpUp(hid, rr, uid, 2);
            JOptionPane.showMessageDialog(this, "거절메시지를 보냈습니다.");

        } else {
            JOptionPane.showMessageDialog(this, "메시지를 선택하세요");
        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RSVPResponse(11);
            }
        });
    }
}
