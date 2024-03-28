import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SearchUI extends JFrame {
    private JTextField placeTextField;
    private JTextField eventTextField;
    private JSpinner startDateSpinner;
    private JSpinner endDateSpinner;

    int uid;
    CalendarMain cal;
    public SearchUI(int id, CalendarMain me) {
        setTitle("일정 검색");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        uid = id;
        cal = me;
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        add(panel);

        panel.add(new JLabel("장소:"));
        placeTextField = new JTextField();
        panel.add(placeTextField);

        panel.add(new JLabel("이벤트 내용:"));
        eventTextField = new JTextField();
        panel.add(eventTextField);

        panel.add(new JLabel("시작 날짜:"));
        startDateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor startDateEditor = new JSpinner.DateEditor(startDateSpinner, "yyyy-MM-dd");
        startDateSpinner.setEditor(startDateEditor);
        panel.add(startDateSpinner);

        panel.add(new JLabel("종료 날짜:"));
        endDateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor endDateEditor = new JSpinner.DateEditor(endDateSpinner, "yyyy-MM-dd");
        endDateSpinner.setEditor(endDateEditor);
        panel.add(endDateSpinner);

        JButton searchButton = new JButton("검색");
        JButton checkButton = new JButton("돌아가기");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchSchedule();
            }
        });

        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 돌아가기 버튼 클릭 시 동작
                dispose();
            }
        });
        panel.add(searchButton);
        panel.add(checkButton);
        setVisible(true);
    }

    private void searchSchedule() {
        SimpleDateFormat dateYear = new SimpleDateFormat("yyyy");
        SimpleDateFormat dateMonth = new SimpleDateFormat("MM");
        SimpleDateFormat dateDay = new SimpleDateFormat("dd");

        String place = placeTextField.getText();
        String event = eventTextField.getText();
        Date startDate = (Date) startDateSpinner.getValue();
        Date endDate = (Date) endDateSpinner.getValue();
        int startYear = Integer.parseInt(dateYear.format(startDate));
        int startMonth = Integer.parseInt(dateMonth.format(startDate));
        int startDay = Integer.parseInt(dateDay.format(startDate));
        int endYear = Integer.parseInt(dateYear.format(endDate));
        int endMonth = Integer.parseInt(dateMonth.format(endDate));
        int endDay = Integer.parseInt(dateDay.format(endDate));
        int[] startArr ={startYear,startMonth,startDay};
        int[] endArr = {endYear, endMonth, endDay};
        ArrayList<EventCon> result = new ArrayList<>();
        //query를 보내 조건에 부합하는 event들을 evevntCon arrayList로 받아옴
        result = new Select(uid).Search(place,event,startArr,endArr);
        new ScheduleListUI(result, uid, cal);



    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SearchUI(8, new CalendarMain(8));
            }
        });
    }
}
