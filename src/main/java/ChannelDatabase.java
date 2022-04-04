import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class ChannelDatabase {
    private Statement statement;

    public ChannelDatabase(Statement statement) throws SQLException {
        this.statement = statement;
        String createTableString = "CREATE TABLE IF NOT EXISTS channels (id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, person BIGINT, channel BIGINT)";
        this.statement.executeUpdate(createTableString);
    }


    /**
     * removes a channel from the database
     * @param person the discord id of the user
     */
    public void removeChannel(Long person){
        try {
            String databaseRemovalString = String.format("DELETE FROM channels WHERE person=%s",person);
            this.statement.executeUpdate(databaseRemovalString);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * adds a channel to the database
     * @param person the discord id of the user
     * @param channel the discord id of the channel
     */
    public void addChannel(Long person, Long channel) {
        try {
            String databaseInsertionString = String.format("INSERT INTO channels (person, channel) VALUES ('%s', '%s')", person, channel);
            this.statement.executeUpdate(databaseInsertionString);
        } catch (Exception e) {
            System.out.println("---> COULD NOT QUERY!");
        }

    }
    /**
     * gets the channels from the database
     * @return a hashmap containing the person-textchannel pairs
     */
    public HashMap<Long,Long> getChannels() {
        HashMap<Long,Long> channels = new HashMap<>();
        try {
            String selectStatementString = "SELECT * FROM channels ORDER BY person";
            ResultSet resultSet = this.statement.executeQuery(selectStatementString);

            while (resultSet.next()) {
                Long person = resultSet.getLong("person");
                Long channel = resultSet.getLong("channel");
                channels.put(person,channel);

            }

        } catch (Exception e) {
            System.out.println("---> COULD NOT FETCH IT!");
        }
        return channels;
    }
}
