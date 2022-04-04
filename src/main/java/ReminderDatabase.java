import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ReminderDatabase {
    Statement statement;

    public ReminderDatabase(Statement statement) throws SQLException {
        this.statement = statement;
        //todo fix this sql string
        String createTableString = "CREATE TABLE IF NOT EXISTS reminders (id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, person BIGINT, channel BIGINT)";
        this.statement.executeUpdate(createTableString);
    }

    public void removeReminder(){
        //todo remove reminders, change header
    }
    public void addReminder(){
        //todo add reminders, change header
    }
    public ArrayList<Reminder> getReminders(){
        // todo return all existing reminders (for start/reboot)
        return null;
    }
}
