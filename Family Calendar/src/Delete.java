import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
public class Delete {
    void Del(int eid, int uid, int oid){
        String SQL_DELETE = "";
        try(Connection conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/chosungwoo","dbms_practice",
                "dbms_practice")) {
            SQL_DELETE = "Delete from user_event where user_id = " + uid + " and event_id = " + eid;
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_DELETE);
            preparedStatement.executeUpdate();
            if(oid == uid){
                SQL_DELETE = "Delete from events where eventid = " + eid;
                preparedStatement = conn.prepareStatement(SQL_DELETE);
                preparedStatement.executeUpdate();
                SQL_DELETE = "Delete from user_event where event_id = " + eid;
                preparedStatement = conn.prepareStatement(SQL_DELETE);
                preparedStatement.executeUpdate();
            }
            conn.close();
            System.out.println("Delete finish");
        } catch(SQLException e){
            System.out.print(e.getMessage());
            new FailureMessageUI();
        } catch(Exception e){
            e.printStackTrace();
        }

    }
    void RSVPDel(int gid, int hid, String r_content){
        String SQL_DELETE = "";
        try(Connection conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/chosungwoo","dbms_practice",
                "dbms_practice")) {
            SQL_DELETE = "Delete from rsvps where gid = " + gid + " and hid = " + hid +" and r_content = "+ "'"+ r_content + "'";
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_DELETE);
            preparedStatement.executeUpdate();
            conn.close();
            System.out.println("RSVP Delete finish");
        } catch(SQLException e){
            System.out.print(e.getMessage());
            new FailureMessageUI();
        } catch(Exception e){
            e.printStackTrace();
        }

    }

}
