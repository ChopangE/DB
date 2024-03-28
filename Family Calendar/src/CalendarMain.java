import jdk.jfr.Event;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.*;

public class CalendarMain extends JFrame implements ActionListener{
    //------------외형구현---------------
    Calendar cal; //케린다
    int year, month, date, uid;
    JPanel pane = new JPanel();

    JPanel rightPane = new JPanel();
    JPanel leftPane = new JPanel();
    JPanel underPane = new JPanel();
    //위에 버튼 추가
    JButton btn1 = new JButton("◀");  //이전버튼
    JButton btn2 = new JButton("▶"); //다음버튼
    ArrayList<EventCon> Ev;
    //위에 라벨추가
    JLabel yearlb = new JLabel("년");
    JLabel monthlb = new JLabel("월");
    CalendarMain me;
    //년월 추가
    JComboBox<Integer> yearCombo = new JComboBox<Integer>();
    DefaultComboBoxModel<Integer> yearModel = new DefaultComboBoxModel<Integer>();
    JComboBox<Integer> monthCombo = new JComboBox<Integer>();
    DefaultComboBoxModel<Integer> monthModel = new DefaultComboBoxModel<Integer>();

    //패널추가
    JPanel pane2 = new JPanel(new BorderLayout());
    JPanel title = new JPanel(new GridLayout(1, 7));
    String titleStr[] = {"일", "월", "화", "수", "목", "금", "토"};
    JPanel datePane = new JPanel(new GridLayout(0, 7));

    //화면디자인
    public CalendarMain(int nu) {
        //------년도 월 구하기------------
        cal = Calendar.getInstance(); //현재날짜
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH)+1;
        date = cal.get(Calendar.DATE);
        uid = nu;
        me = this;
        //년
        for(int i=year-50; i<=year+50; i++){
            yearModel.addElement(i);
        }

        yearCombo.setModel(yearModel);
        yearCombo.setSelectedItem(year);

        //월
        for(int i=1; i<=12; i++) {
            monthModel.addElement(i);
        }
        monthCombo.setModel(monthModel);
        monthCombo.setSelectedItem(month);

        //월화수목금토일
        for(int i=0; i<titleStr.length; i++){
            JLabel lbl = new JLabel(titleStr[i], JLabel.CENTER);
            if(i == 0){
                lbl.setForeground(Color.red);
            }else if(i == 6){
                lbl.setForeground(Color.blue);
            }
            title.add(lbl);
        }
        //날짜 출력
        Update();
        //----------------------------
        setTitle("Family Calendar");
        pane.add(btn1);
        pane.add(yearCombo);
        pane.add(yearlb);
        pane.add(monthCombo);
        pane.add(monthlb);
        pane.add(btn2);
        pane.setBackground(Color.CYAN);
        add(BorderLayout.NORTH, pane);
        pane2.add(title,"North");
        pane2.add(datePane);
        add(BorderLayout.CENTER, pane2);
        JButton bottomButton = new JButton("일정 추가");
        JButton bottomButton2 = new JButton("일정 검색");
        JButton bottomRight = new JButton("특정 시간 가능 인원 확인");
        JButton bottomRight2 = new JButton("회원 정보 수정");
        JButton bottomRight3 = new JButton("전체 가족 목록 확인");

        underPane.add(bottomButton);
        underPane.add(bottomButton2);

        rightPane.setLayout(new BoxLayout(rightPane,BoxLayout.Y_AXIS));
        rightPane.add(bottomRight);
        rightPane.add(Box.createVerticalStrut(10));
        rightPane.add(bottomRight2);
        rightPane.add(Box.createVerticalStrut(10));
        rightPane.add(bottomRight3);


        JButton bottomLeft = new JButton("RSVP 보내기");
        JButton bottomLeft2 = new JButton("RSVP 수신함");
        JButton bottomLeft3 = new JButton("RSVP 송신함");

        leftPane.add(bottomLeft);
        leftPane.add(Box.createVerticalStrut(10));
        leftPane.add(bottomLeft2);
        leftPane.add(Box.createVerticalStrut(10));
        leftPane.add(bottomLeft3);

        leftPane.setLayout(new BoxLayout(leftPane,BoxLayout.Y_AXIS));

        rightPane.setBackground(Color.YELLOW);
        underPane.setBackground(Color.YELLOW);
        leftPane.setBackground(Color.YELLOW);

        bottomButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DateInputUI dateInputUI = new DateInputUI(uid,me);
            }
        });

        bottomButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new SearchUI(uid,me);
            }
        });

        bottomRight.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DateTimeInputUI dateTimeInputUI= new DateTimeInputUI();
            }
        });
        bottomRight2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Infomo().Infomodify(uid);
            }
        });
        bottomRight3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 new PrintUserList(uid);
            }
        });
        bottomLeft.addActionListener(new ActionListener() {
            //rsvp 보내기
            public void actionPerformed(ActionEvent e) {
                new UserList(uid);
            }
        });
        bottomLeft2.addActionListener(new ActionListener() {
            //rsvp 수신함
            public void actionPerformed(ActionEvent e) {
                new RSVPResponse(uid);
            }
        });
        bottomLeft3.addActionListener(new ActionListener() {
            //rsvp 송신함
            public void actionPerformed(ActionEvent e) {
                new RSVPRequest(uid);
            }
        });

        add(BorderLayout.SOUTH, underPane);
        add(BorderLayout.EAST, rightPane);
        add(BorderLayout.WEST, leftPane);

        //각종 명령어
        setVisible(true);
        setSize(1500,750);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //----------기능구현----------
        btn1.addActionListener(this);
        btn2.addActionListener(this);
        yearCombo.addActionListener(this);
        monthCombo.addActionListener(this);
    }


    public void actionPerformed(ActionEvent e) {
        Object eventObj = e.getSource();
        if(eventObj instanceof JComboBox) {
            datePane.setVisible(false);	//보여지는 패널을 숨긴다.
            datePane.removeAll();	//라벨 지우기
            day((Integer)yearCombo.getSelectedItem(), (Integer)monthCombo.getSelectedItem());
            datePane.setVisible(true);	//패널 재출력
        }else if(eventObj instanceof JButton) {
            JButton eventBtn = (JButton) eventObj;
            int yy = (Integer)yearCombo.getSelectedItem();
            int mm = (Integer)monthCombo.getSelectedItem();
            if(eventBtn.equals(btn1)){	//전달
                if(mm==1){
                    yy--; mm=12;
                }else{
                    mm--;
                }
            }else if(eventBtn.equals(btn2)){	//다음달
                if(mm==12){
                    yy++; mm=1;
                }else{
                    mm++;
                }
            }
            yearCombo.setSelectedItem(yy);
            monthCombo.setSelectedItem(mm);
        }
    }
    public void Update(){
        //query를 보내 해당 uid를 가진 유저가 가지고 있는 모든 events들을 EventCon Arraylist 형식으로 가지고 온다.
        Ev = new Select("user_event").check(uid);
        datePane.setVisible(false);	//보여지는 패널을 숨긴다.
        datePane.removeAll();	//라벨 지우기
        day((Integer)yearCombo.getSelectedItem(), (Integer)monthCombo.getSelectedItem());
        datePane.setVisible(true);	//패널 재출력

    }

    public void day(int year, int month) {
        Calendar date = Calendar.getInstance(); // 오늘날짜 + 시간
        date.set(year, month - 1, 1);
        int week = date.get(Calendar.DAY_OF_WEEK);
        int lastDay = date.getActualMaximum(Calendar.DAY_OF_MONTH);

        // 공백출력
        for (int space = 1; space < week; space++) {
            datePane.add(new JLabel("\t"));
        }

        // 날짜 출력
        for (int day = 1; day <= lastDay; day++) {
            //user가 가지고 있는 전체 이벤트 목록인 Ev를 확인하여(Update함수를 통해 목록 가지고 이미 가지고 있음.) 현재 날짜에 진행되는 event를 tmpEv로 가지고 온다.
            ArrayList<EventCon> tmpEv = new ArrayList<>();
            if(Ev != null) {
                for (int i = 0; i < Ev.size(); i++) {
                    if (Ev.get(i).check(year, month, day)) {
                        tmpEv.add(Ev.get(i));
                    }
                }
            }
            JButton btn = new JButton(String.valueOf(day));
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //당일 이벤트를 표시하는 SshedulListUI를 호출하며 당일 이벤트 목록인 tmpEv도 같이 넘겨준다. me는 자신의 인스턴스인데
                    // ScheduleListUI에서 Event목록을 수정/삭제하고 Update를 호출하는데 사용된다.
                    new ScheduleListUI(tmpEv, uid, me);
                    Update();
                }
            });


            cal.set(year, month - 1, day);
            int Week = cal.get(Calendar.DAY_OF_WEEK);
            if (Week == 1) {
                btn.setForeground(Color.red);
            } else if (Week == 7) {
                btn.setForeground(Color.BLUE);
            }

            String res = "";
            //날짜 옆에 간략하게 이벤트 내용을 표시한다.
            if(!tmpEv.isEmpty()) {
                for(int i = 0; i< tmpEv.size(); i++) {
                    res = res +"\n" +tmpEv.get(i).content;
                }

            }
            String dateText = day + "\n\n" + res;
            btn.setText("<html>" + dateText.replaceAll("\n", "<br>") + "</html>");
            datePane.add(btn);

        }
    }


    //실헹메소드
    public static void main(String[] args) {
        CalendarMain cl = new CalendarMain(8);
    }
}