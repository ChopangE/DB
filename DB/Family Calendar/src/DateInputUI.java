import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.*;

public class DateInputUI extends JFrame {
    private JComboBox<String> yearComboBox, monthComboBox, hourComboBox, minuteComboBox, timeInterval;
    private JTextField dayTextField, locationTextField, textInputField, eventIdField, timeFrame,duration;
    private JRadioButton modifyRadioButton, addRadioButton, deleteRadioButton;
    private JButton submitButton,addButton;
    private int uid = 0;

    CalendarMain cal;
    public DateInputUI(int num, CalendarMain me) {
        super("이벤트 관리");
        uid = num;
        cal = me;
        createUI(uid);
    }

    private void createUI(int n) {
        JPanel panel = new JPanel();

        String[] years = {"2023", "2024", "2025"};
        yearComboBox = new JComboBox<>(years);

        String[] months = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        monthComboBox = new JComboBox<>(months);

        dayTextField = new JTextField(5);

        String[] hours = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12",
                "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
        hourComboBox = new JComboBox<>(hours);

        String[] minutes = {"00", "15", "30", "45"};
        minuteComboBox = new JComboBox<>(minutes);
        timeInterval = new JComboBox<>(minutes);

        timeFrame = new JTextField(10);
        locationTextField = new JTextField(10);
        textInputField = new JTextField(10);
        duration = new JTextField(5);


        addRadioButton = new JRadioButton("추가");


        submitButton = new JButton("확인");
        addButton = new JButton("추가");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                    String year = (String) yearComboBox.getSelectedItem();
                    String month = (String) monthComboBox.getSelectedItem();
                    String day = dayTextField.getText();
                    String hour = (String) hourComboBox.getSelectedItem();
                    String minute = (String) minuteComboBox.getSelectedItem();
                    String location = locationTextField.getText();
                    String textInput = textInputField.getText();
                    String tI = (String)timeInterval.getSelectedItem();
                    String tF = timeFrame.getText();
                    String du = duration.getText();
                    //이벤트 세부사항을 입력받고 초대할 사람들을 선택한다.
                    new PrintUserList(uid); //현재 DB에 존재하는 모든 user들의 name을 띄운다.
                    new InvitationUI(year,month,day,hour,minute,location,textInput,tI,tF,du,n,cal); //실제 초대할 user들의 이름을 입력받는 UI

                    dispose();


            }
        });

        panel.add(new JLabel("년:"));
        panel.add(yearComboBox);
        panel.add(new JLabel("월:"));
        panel.add(monthComboBox);
        panel.add(new JLabel("일:"));
        panel.add(dayTextField);
        panel.add(new JLabel("시간:"));
        panel.add(hourComboBox);
        panel.add(new JLabel("분:"));
        panel.add(minuteComboBox);
        panel.add(new JLabel("장소:"));
        panel.add(locationTextField);
        panel.add(new JLabel("이벤트 내용:"));
        panel.add(textInputField);
        panel.add(new JLabel("일정 지속시간(분)"));
        panel.add(duration);
        panel.add(new JLabel("알림 간격"));
        panel.add(timeInterval);
        panel.add(new JLabel("알림 Frame"));
        panel.add(timeFrame);

        panel.add(addButton);
        panel.add(submitButton);
        getContentPane().add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new DateInputUI(2, new CalendarMain(0));
            }
        });
    }
}
