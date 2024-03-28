import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DateModify extends JFrame {
    private JComboBox<String> yearComboBox, monthComboBox, hourComboBox, minuteComboBox, timeInterval;
    private JTextField dayTextField, locationTextField, textInputField, eventIdField, timeFrame,duration;
    private JRadioButton modifyRadioButton, addRadioButton, deleteRadioButton;
    private JButton submitButton;
    private int uid = 0;
    int eid;
    int oid;
    CalendarMain cal;
    public DateModify(int num, int e, int o, CalendarMain me) {
        super("이벤트 관리");
        cal = me;
        uid = num;
        eid = e;
        oid = o;
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
        //eventIdField = new JTextField(5);
        duration = new JTextField(5);


        //modifyRadioButton = new JRadioButton("수정");
        //addRadioButton = new JRadioButton("추가");
        //deleteRadioButton = new JRadioButton("삭제");

        ButtonGroup buttonGroup = new ButtonGroup();
        //buttonGroup.add(modifyRadioButton);
        //buttonGroup.add(addRadioButton);
        //buttonGroup.add(deleteRadioButton);

        submitButton = new JButton("확인");

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String year = (String) yearComboBox.getSelectedItem();
                String month = (String) monthComboBox.getSelectedItem();
                String day = dayTextField.getText();
                String hour = (String) hourComboBox.getSelectedItem();
                String minute = (String) minuteComboBox.getSelectedItem();
                String location = locationTextField.getText();
                String content = textInputField.getText();
                String tI = (String)timeInterval.getSelectedItem();
                String tF = timeFrame.getText();
                String du = duration.getText();
                String[] mo = {year,month, day,hour,minute,location,content,du,tI,tF};
                if(oid == uid){
                    //event소유자인 경우에만 query를 날려 eid에 해당하는 event를 입력된 값으로 수정한다.
                    new Update().Up(mo,eid);
                    JOptionPane.showMessageDialog(DateModify.this, "수정하였습니다 ");
                    //여기서 바로 calendar update한다.
                    cal.Update();
                    dispose();
                }
                else{
                    //event소유자가 아닐 경우 에러메시지를 발생시킨다.
                    JOptionPane.showMessageDialog(DateModify.this, "Event 소유자가 아니라 수정할 수 없습니다. ");
                    dispose();
                }

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

        panel.add(submitButton);

        getContentPane().add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        new DateModify(2,0,0,new CalendarMain(0));

    }
}