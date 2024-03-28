import java.sql.*;
import java.util.Date;
import java.util.ArrayList;
public class Insert {
    String table;
    ArrayList<String> cols = new ArrayList<>();
    String targets[];
    int uid;
    int targetNum;
    public Insert(int n){
        uid = n;
    }
    public Insert(String ta,String []arr, int n){
        this(ta,arr,n,null,0);
    }


    public Insert(String ta,String []arr, int n, String []arr2, int n2){
        table = ta;
        targetNum = n2;
        for(int i = 0; i < n; i++){
            cols.add(arr[i]);
        }
        targets = arr2;
    }
    void Start(){
        String SQL_INSERT = "";
        if(table.equals("users")) {
            SQL_INSERT = "INSERT INTO users (id, password, name, position) VALUES (?, ?, ?, ?)";
            try(Connection conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/chosungwoo","dbms_practice",
                    "dbms_practice");
                PreparedStatement preparedStatement = conn.prepareStatement(SQL_INSERT)) {
                preparedStatement.setString(1, cols.get(0));
                preparedStatement.setString(2, cols.get(1));
                preparedStatement.setString(3, cols.get(2));
                preparedStatement.setString(4, cols.get(3));
                preparedStatement.executeUpdate();
                System.out.println("Success Insert");
                conn.close();
                System.out.println("Success Close");
            } catch(SQLException e){
                System.out.print(e.getMessage());
                new FailureMessageUI();

            } catch(Exception e){
                e.printStackTrace();
            }
        }
        else if(table.equals("events")){
            int eventid = new Select("serial").Start() + 1;
            System.out.println("eventId = " + eventid);
            SQL_INSERT = "INSERT INTO events (year, month, day, hour, min, location, content, duration, interval, timeframe, owner) VALUES (?, ?, ?, ?" +
                    ", ?, ?, ?, ?, ?, ?, ?)";
            try(Connection conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/chosungwoo","dbms_practice",
                    "dbms_practice");
                PreparedStatement preparedStatement = conn.prepareStatement(SQL_INSERT)) {
                preparedStatement.setInt(1, Integer.parseInt(cols.get(0)));
                preparedStatement.setInt(2, Integer.parseInt(cols.get(1)));
                preparedStatement.setInt(3, Integer.parseInt(cols.get(2)));
                preparedStatement.setInt(4, Integer.parseInt(cols.get(3)));
                preparedStatement.setInt(5, Integer.parseInt(cols.get(4)));
                preparedStatement.setString(6, cols.get(5));
                preparedStatement.setString(7, cols.get(6));
                preparedStatement.setInt(8, Integer.parseInt(cols.get(7)));
                preparedStatement.setInt(9, Integer.parseInt(cols.get(8)));
                preparedStatement.setInt(10, Integer.parseInt(cols.get(9)));
                preparedStatement.setInt(11, Integer.parseInt(cols.get(10)));
                preparedStatement.executeUpdate();

                System.out.println("Success Insert");
                conn.close();
                System.out.println("Success Close");

            } catch(SQLException e){
                System.out.print(e.getMessage());
                new FailureMessageUI();

            } catch(Exception e){
                e.printStackTrace();
            }
            int uids[] = new int[1+targetNum];
            uids[0] = Integer.parseInt(cols.get(10));
            if(targetNum > 0){
                int tmp[];
                tmp = new Select("users").Start(targets);
                for(int i = 0; i < targetNum; i++){
                    uids[i+1] = tmp[i];
                }
            }
            SQL_INSERT = "INSERT INTO user_event (user_id, event_id) VALUES(?, ?)";

            try(Connection conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/chosungwoo","dbms_practice",
                    "dbms_practice")) {
                PreparedStatement preparedStatement = conn.prepareStatement(SQL_INSERT);
                for(int i = 0; i < uids.length; i++) {
                    preparedStatement.setInt(1, uids[i]);
                    preparedStatement.setInt(2, eventid);
                    preparedStatement.executeUpdate();
                }
                conn.close();
                System.out.println("Success User_event table insert");
            } catch(SQLException e){
                System.out.print(e.getMessage());
                new FailureMessageUI();

            } catch(Exception e){
                e.printStackTrace();
            }

        }


    }
    void Rsvp(int hid, int gid, String r_content, int r_hour, int r_min, int r_check){
        String SQL_INSERT ="";
        SQL_INSERT = "INSERT INTO rsvps (hid, gid, r_content, r_hour, r_min, r_check) VALUES (?, ?, ?, ?, ?, ?)";
        try(Connection conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/chosungwoo","dbms_practice",
                "dbms_practice")) {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_INSERT);
            preparedStatement.setInt(1, hid);
            preparedStatement.setInt(2, gid);
            preparedStatement.setString(3,r_content);
            preparedStatement.setInt(4, r_hour);
            preparedStatement.setInt(5, r_min);
            preparedStatement.setInt(6, r_check);
            preparedStatement.executeUpdate();
            conn.close();
            System.out.println("Success RSVPS table insert");
        } catch(SQLException e){
            System.out.print(e.getMessage());
            new FailureMessageUI();

        } catch(Exception e){
            e.printStackTrace();
        }

    }

}
