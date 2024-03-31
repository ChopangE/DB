import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ScheduleListUI extends JFrame {
    private DefaultListModel<String> scheduleListModel;
    private JList<String> scheduleList;
    private JTextField scheduleTextField;
    int uid;
    private ArrayList<EventCon> st;
    CalendarMain cal;
    public ScheduleListUI(){
        this(new ArrayList<EventCon>(),0, null);
    }
    public ScheduleListUI(ArrayList<EventCon> stt, int id, CalendarMain me) {
        // 프레임 생성
        super("Schedule List");
        st = stt;
        // 레이아웃 설정
        setLayout(new BorderLayout());
        uid = id;
        cal = me;
        scheduleListModel = new DefaultListModel<>();
        scheduleList = new JList<>(scheduleListModel);

        JScrollPane scrollPane = new JScrollPane(scheduleList);
        add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new FlowLayout());


        JButton editButton = new JButton("일정 수정");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editSchedule();
            }
        });
        inputPanel.add(editButton);

        JButton removeButton = new JButton("일정 제거");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeSchedule();
            }
        });
        inputPanel.add(removeButton);

        JButton OKButton = new JButton("확인");
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        inputPanel.add(OKButton);


        add(inputPanel, BorderLayout.SOUTH);

        // 일정 출력
        if(st != null) {
            EventCon ev;
            for (int i = 0; i < st.size(); i++) {
                ev = st.get(i);
                scheduleListModel.addElement("" + ev.hour +"시 "+ ev.min + "분 " + ev.location+"에서 " + ev.content+"가 "+ ev.duration + "(분)동안 " +"진행 될 예정.");
            }

        }
        // 프레임 설정
        setSize(700, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }


    private void editSchedule() {
        int selectedIndex = scheduleList.getSelectedIndex();
        if (selectedIndex != -1) {
            int eid = st.get(selectedIndex).event_id;
            int oid = st.get(selectedIndex).owner;
            //해당 event를 수정하는 UI인 DateModify를 호출한다.
            new DateModify(uid,eid,oid,cal);
            dispose();
        } else {
            //일정이 선택되지 않은 경우.
            JOptionPane.showMessageDialog(this, "수정할 일정을 선택하세요.");
        }
    }

    private void removeSchedule() {
        int selectedIndex = scheduleList.getSelectedIndex();
        if (selectedIndex != -1) {
            int eid = st.get(selectedIndex).event_id;
            int oid = st.get(selectedIndex).owner;
            //여기서 Delete query 만약 본인이 owner면 전체 삭제(event table, user_event table에서 삭제) 아니면 본인만 삭제(user_event table에서 삭제)
            new Delete().Del(eid, uid, oid);
            //여기서 calendar update
            st.remove(selectedIndex);
            cal.Update();
            scheduleListModel.removeElementAt(selectedIndex);
        } else {
            //일정이 선택되지 않은 경우
            JOptionPane.showMessageDialog(this, "제거할 일정을 선택하세요.");
        }

    }

    public static void main(String[] args) {

                new ScheduleListUI();


    }
}
