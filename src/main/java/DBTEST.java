import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class DBTEST {


    public void readDataBase() {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            Connection connect = DriverManager
                    .getConnection("jdbc:mysql://172.18.0.2:3306/data?"
                            + "user=user&password=138448");


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new DBTEST().readDataBase();
    }
}
