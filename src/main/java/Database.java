import java.sql.*;
import java.util.HashMap;

public class Database {
	private String url;
	private Statement statement;

	/**
	 * constructor
	 * creates a table for the chat if none already exists
	 */
	public Database() {
		// database information, add your own to test the program (as mine should be private)
		final String db_name = SecretStrings.getDBName();
		final String username = SecretStrings.getUserName();
		final String password = SecretStrings.getPassword();
		final String computer = SecretStrings.getComputer();
		final String port = SecretStrings.getPort();

		this.url = "jdbc:mysql://" + computer + ":" +port+ "/" + db_name;

		try {

			Class.forName("com.mysql.jdbc.Driver");


			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			Connection connection = DriverManager.getConnection(url, username,password);

			this.statement = connection.createStatement();
			this.statement.execute("DROP TABLE channels");
			String createTableString = "CREATE TABLE IF NOT EXISTS channels (id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, person BIGINT, channel BIGINT)";
			this.statement.executeUpdate(createTableString);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("---> COULD NOT CONNECT OR CREATE TABLE!");
			System.exit(-1);
		}

	}

	public void removeChannel(Long person){
		try {
			String databaseRemovalString = String.format("DELETE FROM channels WHERE person=%s",person);
			this.statement.executeUpdate(databaseRemovalString);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addChannel(Long person, Long channel) {


		try {
			String databaseInsertionString = String.format("INSERT INTO channels (person, channel) VALUES ('%s', '%s')", person, channel);
			this.statement.executeUpdate(databaseInsertionString);
		} catch (Exception e) {
			System.out.println("---> COULD NOT QUERY!");
		}

	}
		/*
     * gets the chat from the database
     * @return a html string of the messages
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