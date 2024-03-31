import javax.lang.model.type.ArrayType;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RSVPRequest extends JFrame {
    private DefaultListModel<String> scheduleListModel;
    private JList<String> scheduleList;
    private JTextField scheduleTextField;
    int uid;

    ArrayList<String> str = new ArrayList<String>();
    public RSVPRequest(){
        this(0);
    }
    public RSVPRequest(int id) {
        super("RSVP 송신함");
        setLayout(new BorderLayout());
        uid = id;
        scheduleListModel = new DefaultListModel<>();
        scheduleList = new JList<>(scheduleListModel);

        JScrollPane scrollPane = new JScrollPane(scheduleList);
        add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new FlowLayout());


        JButton deleteButton = new JButton("삭제");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSchedule();
            }
        });
        inputPanel.add(deleteButton);

        JButton OKButton = new JButton("확인");
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        inputPanel.add(OKButton);


        add(inputPanel, BorderLayout.SOUTH);
        //uid로 rsvps table에서 hid = uid인 rsvp를 찾고 gid를 통해 users table에서 해당 gid를 가진 name도 찾아냄
        str = new Select(uid).requestRsvp(uid);

        for(int i = 0; i < str.size(); i = i+3){
            scheduleListModel.addElement("수신자 : " + str.get(i) +", 이벤트 내용 : "+ str.get(i+1) +"   답신 결과 : " +str.get(i+2));
        }

        // 일정 출력



        // 프레임 설정
        setSize(700, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }


    private void acceptSchedule() {
        int selectedIndex = scheduleList.getSelectedIndex();
        if (selectedIndex != -1) {


            JOptionPane.showMessageDialog(this, "수락메시지를 보냈습니다.");

        } else {
            JOptionPane.showMessageDialog(this, "메시지를 선택하세요");
        }
    }

    private void deleteSchedule() {
        int selectedIndex = scheduleList.getSelectedIndex();
        if (selectedIndex != -1) {
            int gid = new Select(uid).nametoUid(str.get(selectedIndex*3));
            int hid = uid;
            String r_content = str.get(selectedIndex * 3 +1);
            new Delete().RSVPDel(gid,hid,r_content);
            str.remove(3*selectedIndex + 2);
            str.remove(3*selectedIndex + 1);
            str.remove(3*selectedIndex);
            scheduleListModel.removeElementAt(selectedIndex);
            JOptionPane.showMessageDialog(this, "RSVP요청이 삭제되었습니다.");
        } else {
            JOptionPane.showMessageDialog(this, "메시지를 선택하세요");
        }

    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RSVPRequest(8);
            }
        });
    }
}
