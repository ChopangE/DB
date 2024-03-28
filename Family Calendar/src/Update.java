import java.sql.*;

public class Update {

    void Up(String[] mo, int eid){
        String SQL_UPDATE = "";
        try(Connection conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/chosungwoo","dbms_practice",
                "dbms_practice")) {
            SQL_UPDATE = "UPDATE events SET year = " + mo[0] + ", month = " + mo[1]+ ", day = " + mo[2] +", hour = " + mo[3]+ "" +
                    ", min = " + mo[4] +", location = " + "'" + mo[5] +"'" +", content = " + "'"+mo[6] + "'" +", duration = " + mo[7] + ", interval = " +
                    ""+ mo[8] + ", timeframe = " + mo[9]+ " where eventid = " + eid;
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_UPDATE);
            preparedStatement.executeUpdate();
            conn.close();
            System.out.println("Update finish");
        } catch(SQLException e){
            System.out.print(e.getMessage());
            new FailureMessageUI();
        } catch(Exception e){
            e.printStackTrace();
        }


    }
    void Use(String[] tmp, int uid){
        String SQL_UPDATE = "";
        try(Connection conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/chosungwoo","dbms_practice",
                "dbms_practice")) {
            SQL_UPDATE = "UPDATE users SET id = " + "'"+tmp[0] +"'"+  ", password = " + "'"+ tmp[1]+ "'"+", name = " + "'"+tmp[2]+"'" +", position = " + "'"+tmp[3]+"'" + " where uid = " +uid;
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_UPDATE);
            preparedStatement.executeUpdate();
            conn.close();
            System.out.println("Update finish");
        } catch(SQLException e){
            System.out.print(e.getMessage());
            new FailureMessageUI();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    void RsvpUp(int hid, String r_content, int gid ,int check){
        String SQL_UPDATE = "";
        try(Connection conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/chosungwoo","dbms_practice",
                "dbms_practice")) {
            SQL_UPDATE = "UPDATE rsvps SET r_check = " + check + " where hid = "+ hid+ " and gid = " + gid + " and r_content = " + "'" + r_content+ "'";
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_UPDATE);
            preparedStatement.executeUpdate();
            conn.close();
            System.out.println("Update finish");
        } catch(SQLException e){
            System.out.print(e.getMessage());
            new FailureMessageUI();

        } catch(Exception e){
            e.printStackTrace();
        }
    }

}
