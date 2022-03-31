import java.sql.*;
import java.util.HashMap;

public class Database {
	private Statement statement;

	/**
	 * constructor
	 * creates a table for the channels if none already exists
	 */
	public Database() {
		//ugly solution
		SecretStrings.COMPUTER.setComputer();

		// database information, add your own to test the program (as mine should be private)
		final String db_name = SecretStrings.DB_NAME.getValue();
		final String username = SecretStrings.USERNAME.getValue();
		final String password = SecretStrings.PASSWORD.getValue();
		final String computer = SecretStrings.COMPUTER.getValue();
		final String port = SecretStrings.PORT.getValue();


		String url = "jdbc:mysql://" + computer + ":" + port + "/" + db_name;
	while (true){
		try {

			Class.forName("com.mysql.jdbc.Driver");


			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			Connection connection = DriverManager.getConnection(url, username,password);

			this.statement = connection.createStatement();
			//this.statement.execute("DROP TABLE IF EXISTS channels");
			String createTableString = "CREATE TABLE IF NOT EXISTS channels (id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, person BIGINT, channel BIGINT)";
			this.statement.executeUpdate(createTableString);
			System.out.println("database done");
			break;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("---> COULD NOT CONNECT OR CREATE TABLE!");
		}
	}


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