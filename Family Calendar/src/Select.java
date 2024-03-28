import java.sql.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

public class Select {
    String table;
    int success = -1;
    ArrayList<String> cols = new ArrayList<>();
    int uid;

    public Select(int n) {
        uid = n;
    }

    public Select(String ta) {
        this(ta, null, 0);
    }

    public Select(String ta, String[] arr, int n) {
        table = ta;
        for (int i = 0; i < n; i++) {
            cols.add(arr[i]);
        }
    }

    int Start() {
        String SQL_SELECT = "";
        ResultSet rs = null;
        if (table.equals("users")) {
            SQL_SELECT = "SELECT * from users " + "Where id = " + "'" + cols.get(0) + "'";
            System.out.println(SQL_SELECT);
            try (Connection conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/chosungwoo", "dbms_practice",
                    "dbms_practice")) {
                rs = conn.createStatement().executeQuery(SQL_SELECT);
                System.out.println("Success Query");
                while (rs.next()) {
                    String pw = rs.getString("password");
                    int pn = rs.getInt("uid");
                    if (pw.equals(cols.get(1))) {
                        success = pn;
                    } else {
                        success = -1;
                    }
                }
                conn.close();
                System.out.println("Finish");
            } catch (SQLException e) {
                System.out.print(e.getMessage());
                new FailureMessageUI();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (table.equals("events")) {

        } else if (table.equals("serial")) {

            SQL_SELECT = "SELECT last_value FROM events_eventid_seq";
            System.out.println(SQL_SELECT);
            try (Connection conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/chosungwoo", "dbms_practice",
                    "dbms_practice")) {
                rs = conn.createStatement().executeQuery(SQL_SELECT);
                System.out.println("Success Query");
                while (rs.next()) {
                    success = rs.getInt("last_value");
                }
                conn.close();
                System.out.println("serial : " + success);
                System.out.println("Finish");
            } catch (SQLException e) {
                System.out.print(e.getMessage());
                new FailureMessageUI();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return success;

    }
    int nametoUid(String name){
        int tmp = 0;
        String SQL_SELECT = "";
        ResultSet rs = null;
        SQL_SELECT = "SELECT uid FROM users where name = " + "'" + name +"'";
        System.out.println(SQL_SELECT);
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/chosungwoo", "dbms_practice",
                "dbms_practice")) {
            rs = conn.createStatement().executeQuery(SQL_SELECT);
            while (rs.next()) {
                tmp = rs.getInt("uid");
            }

            System.out.println("name to uid Finish");
        } catch (SQLException e) {
            System.out.print(e.getMessage());
            new FailureMessageUI();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tmp;
    }
    ArrayList<String> responseRsvp(int gid){
        ArrayList<String> res = new ArrayList<String>();
        String SQL_SELECT = "";
        ResultSet rs = null;
        SQL_SELECT = "SELECT name, r_content,r_hour, r_min FROM rsvps, users where uid = hid and gid = " + gid;
        LocalTime now = LocalTime.now();

        DateTimeFormatter hour = DateTimeFormatter.ofPattern("HH");
        DateTimeFormatter min = DateTimeFormatter.ofPattern("mm");
        int hour2 = Integer.parseInt(now.format(hour));
        int min2 = Integer.parseInt(now.format(min));
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/chosungwoo", "dbms_practice",
                "dbms_practice")) {
            rs = conn.createStatement().executeQuery(SQL_SELECT);
            while (rs.next()) {
                int r_hour = rs.getInt("r_hour");
                int r_min = rs.getInt("r_min");
                if((r_hour * 60 + r_min) + 10 >= hour2 * 60 + min2) {
                    res.add(rs.getString("name"));
                    res.add(rs.getString("r_content"));
                }
            }
            conn.close();
            System.out.println("responsersvp Finish");
        } catch (SQLException e) {
            System.out.print(e.getMessage());
            new FailureMessageUI();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    ArrayList<String> requestRsvp(int hid){
        ArrayList<String> res = new ArrayList<String>();
        String SQL_SELECT = "";
        ResultSet rs = null;
        int r_hour,r_min;
        SQL_SELECT = "SELECT * FROM rsvps, users where uid = gid and hid = " + hid;
        System.out.println(SQL_SELECT);
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/chosungwoo", "dbms_practice",
                "dbms_practice")) {
            rs = conn.createStatement().executeQuery(SQL_SELECT);
            while (rs.next()) {
                res.add(rs.getString("name"));
                res.add(rs.getString("r_content"));
                r_hour = rs.getInt("r_hour");
                r_min = rs.getInt("r_min");

                if(rs.getInt("r_check") == 0){
                    LocalTime now = LocalTime.now();
                    DateTimeFormatter hour = DateTimeFormatter.ofPattern("HH");
                    DateTimeFormatter min = DateTimeFormatter.ofPattern("mm");
                    int hour2 = Integer.parseInt(now.format(hour));
                    int min2 = Integer.parseInt(now.format(min));
                    if(hour2*60 + min2 > (r_hour*60 + r_min) + 10) {
                        res.add("거절함");
                    }
                    else{
                        res.add("확인전");
                    }
                }
                else if(rs.getInt("r_check") == 1){
                    res.add("수락함");
                }
                else{
                    res.add("거절함");
                }
            }
            conn.close();
            System.out.println("responsersvp Finish");
        } catch (SQLException e) {
            System.out.print(e.getMessage());
            new FailureMessageUI();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    String[] all(){
        String SQL_SELECT = "";
        ResultSet rs = null;
        String[] result;
        ArrayList<String> res = new ArrayList<String>();
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/chosungwoo", "dbms_practice",
                    "dbms_practice")) {
            SQL_SELECT = "SELECT name FROM users ";
            rs = conn.createStatement().executeQuery(SQL_SELECT);
            while (rs.next()) {
                res.add(rs.getString("name"));
            }
            conn.close();
            } catch (SQLException e) {
                System.out.print(e.getMessage());
            new FailureMessageUI();

        } catch (Exception e) {
                e.printStackTrace();
            }
        int n = res.size();
        result = new String[n];
        for(int i = 0; i < n; i++){
            result[i] = res.get(i);
        }
        return result;
    }
    int[] Start(String[] arr) {
        String SQL_SELECT = "";
        ResultSet rs = null;
        int len = arr.length;
        int[] reid = new int[len];
        if (table.equals("users")) {
            try (Connection conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/chosungwoo", "dbms_practice",
                    "dbms_practice")) {
                for (int i = 0; i < len; i++) {
                    SQL_SELECT = "SELECT uid FROM users where name = " + "'" + arr[i] + "'";
                    rs = conn.createStatement().executeQuery(SQL_SELECT);
                    System.out.println("Success Query");
                    while (rs.next()) {
                        reid[i] = rs.getInt("uid");
                    }
                }
                conn.close();
            } catch (SQLException e) {
                System.out.print(e.getMessage());
                new FailureMessageUI();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        System.out.println("success uidCheck");
        return reid;
    }

    ArrayList<String> start(int[] arr) {
        String SQL_SELECT = "";
        ResultSet rs = null;
        ArrayList<String> res = new ArrayList<String>();
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/chosungwoo", "dbms_practice",
                "dbms_practice")) {
            SQL_SELECT = "SELECT name FROM users where uid NOT IN (SELECT user_id from events, user_event Where event_id = eventid and" +
                    " year = " + arr[0] + " and month = " + arr[1] + " and day = " + arr[2] + " and hour * 60 + min + duration>= " + arr[3] + " and" +
                    " hour * 60 + min <= " + arr[3] + ")";
            rs = conn.createStatement().executeQuery(SQL_SELECT);
            System.out.println("Success Query");
            while (rs.next()) {
                res.add(rs.getString("name"));
            }
            conn.close();
        } catch (SQLException e) {
            System.out.print(e.getMessage());
            new FailureMessageUI();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    ArrayList<EventCon> check(int uid) {
        ArrayList<EventCon> Ev = new ArrayList<EventCon>();
        int tmp[] = new int[10];
        String tmp2[] = new String[2];
        String SQL_SELECT = "";
        ResultSet rs = null;
        if (table.equals("user_event")) {
            SQL_SELECT = "SELECT * from user_event, events Where user_id = " + uid + " and event_id = eventid";
            System.out.println(SQL_SELECT);
            try (Connection conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/chosungwoo", "dbms_practice",
                    "dbms_practice")) {
                rs = conn.createStatement().executeQuery(SQL_SELECT);
                System.out.println("Success Query");
                while (rs.next()) {
                    tmp[0] = rs.getInt("eventid");
                    tmp[1] = rs.getInt("year");
                    tmp[2] = rs.getInt("month");
                    tmp[3] = rs.getInt("day");
                    tmp[4] = rs.getInt("hour");
                    tmp[5] = rs.getInt("min");
                    tmp[6] = rs.getInt("duration");
                    tmp[7] = rs.getInt("interval");
                    tmp[8] = rs.getInt("timeframe");
                    tmp[9] = rs.getInt("owner");
                    tmp2[0] = rs.getString("location");
                    tmp2[1] = rs.getString("content");
                    Ev.add(new EventCon(tmp, tmp2));
                }
                conn.close();
                System.out.println("Finish");
            } catch (SQLException e) {
                System.out.print(e.getMessage());
                new FailureMessageUI();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Ev;
    }

    String[] use() {
        String SQL_SELECT = "";
        ResultSet rs = null;
        String[] res = new String[4];
        SQL_SELECT = "SELECT * FROM users where uid = " + uid;
        System.out.println(SQL_SELECT);
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/chosungwoo", "dbms_practice",
                "dbms_practice")) {
            rs = conn.createStatement().executeQuery(SQL_SELECT);
            while (rs.next()) {
                res[0] = rs.getString("id");
                res[1] = rs.getString("password");
                res[2] = rs.getString("name");
                res[3] = rs.getString("position");
            }
            conn.close();
            System.out.println("Finish take user Info");
        } catch (SQLException e) {
            System.out.print(e.getMessage());
            new FailureMessageUI();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    ArrayList<EventCon> Search(String place, String content, int[] start, int[] end){
        String SQL_SELECT = "";
        ResultSet rs = null;
        ArrayList<EventCon> result = new ArrayList<EventCon>();
        int tmp[] = new int[10];
        String tmp2[] = new String[2];

        int startTime = start[0] * 10000 + start[1] * 100 + start[2];
        int endTime = end[0] * 10000 + end[1] * 100 + end[2];
        SQL_SELECT = "SELECT * FROM events, user_event where user_id = " + uid + " and event_id = eventid and content LIKE " + "'%" + content +"%' and location Like " +"'%" +
        place +"%' and " + startTime + " <= year * 10000 + month * 100 + day and " + endTime + " >= year * 10000 + month * 100 + day";
        System.out.println(SQL_SELECT);
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/chosungwoo", "dbms_practice",
                "dbms_practice")) {
            rs = conn.createStatement().executeQuery(SQL_SELECT);
            while (rs.next()) {
                tmp[0] = rs.getInt("eventid");
                tmp[1] = rs.getInt("year");
                tmp[2] = rs.getInt("month");
                tmp[3] = rs.getInt("day");
                tmp[4] = rs.getInt("hour");
                tmp[5] = rs.getInt("min");
                tmp[6] = rs.getInt("duration");
                tmp[7] = rs.getInt("interval");
                tmp[8] = rs.getInt("timeframe");
                tmp[9] = rs.getInt("owner");
                tmp2[0] = rs.getString("location");
                tmp2[1] = rs.getString("content");
                result.add(new EventCon(tmp, tmp2));
            }
            conn.close();
            System.out.println("Finish take user Info");
        } catch (SQLException e) {
            System.out.print(e.getMessage());
            new FailureMessageUI();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        new Select("serial").Start();
    }
}
