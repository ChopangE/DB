import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DateTimeInputUI extends JFrame {
    private JSpinner dateSpinner;
    private JSpinner timeSpinner;

    // 추가: 사용자 입력을 저장할 변수들
    private int selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute;

    public DateTimeInputUI() {
        super("해당 일정 가능 사용자 찾기");

        setLayout(new FlowLayout());

        add(new JLabel("날짜:"));
        dateSpinner = createDateSpinner();
        add(dateSpinner);

        add(new JLabel("시간:"));
        timeSpinner = createTimeSpinner();
        add(timeSpinner);


        JButton confirmButton = new JButton("검색");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 선택된 날짜와 시간 가져오기
                Date selectedDate = (Date) dateSpinner.getValue();
                Date selectedTime = (Date) timeSpinner.getValue();

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(selectedDate);
                selectedYear = calendar.get(Calendar.YEAR);
                selectedMonth = calendar.get(Calendar.MONTH) + 1; // 월은 0부터 시작하므로 1을 더함
                selectedDay = calendar.get(Calendar.DAY_OF_MONTH);

                calendar.setTime(selectedTime);
                selectedHour = calendar.get(Calendar.HOUR_OF_DAY);
                selectedMinute = calendar.get(Calendar.MINUTE);
                int[] go ={selectedYear , selectedMonth , selectedDay, selectedHour * 60 + selectedMinute};
                //선택된 정보들을 바탕으로 여기서 query를 날려 user namelist를 가져옴
                ArrayList<String> str = new Select("hi").start(go);

                JOptionPane.showMessageDialog(DateTimeInputUI.this,
                        "Selected Date: " + selectedYear + "-" + selectedMonth + "-" + selectedDay +
                                "\nSelected Time: " + selectedHour + ":" + selectedMinute);
                dispose();
                //userNameListUI호출
                new UserNameListUI(str);

            }
        });
        add(confirmButton);

        setSize(350, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JSpinner createDateSpinner() {
        SpinnerDateModel dateModel = new SpinnerDateModel();
        JSpinner dateSpinner = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        dateSpinner.setEditor(dateEditor);
        return dateSpinner;
    }

    private JSpinner createTimeSpinner() {
        SpinnerDateModel timeModel = new SpinnerDateModel();
        JSpinner timeSpinner = new JSpinner(timeModel);
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
        timeSpinner.setEditor(timeEditor);
        return timeSpinner;
    }

    public static void main(String[] args) {
        new DateTimeInputUI();
    }
}
