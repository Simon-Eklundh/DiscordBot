import java.sql.*;
import java.util.HashMap;

public class Database {
	private  ReminderDatabase reminderDatabase;
	private Statement statement;
	private ChannelDatabase channelDatabase;
	/**
	 * constructor
	 * creates a table for the channels if none already exists
	 */
	public Database() {

		// database information, add your own to test the program (as mine should be private)
		final String db_name = Credentials.getDB_NAME();
		final String username = Credentials.getUSERNAME();
		final String password = Credentials.getPASSWORD();
		final String computer = Credentials.getIP();
		final String port = Credentials.getPORT();


		String url = "jdbc:mysql://" + computer + ":" + port + "/" + db_name;
		try {

			Class.forName("com.mysql.jdbc.Driver");


			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			Connection connection = DriverManager.getConnection(url, username,password);

			this.statement = connection.createStatement();
			this.channelDatabase  = new ChannelDatabase(statement);
			this.reminderDatabase = new ReminderDatabase(statement);
			//this.statement.execute("DROP TABLE IF EXISTS channels"); //for clearing the channels table for the tests

			System.out.println("database connection done");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("---> COULD NOT CONNECT OR CREATE TABLE!");
		}
	}


	public void removeChannel(Long userID) {
		channelDatabase.removeChannel(userID);
	}

	public void addChannel(long person, long channel) {
		channelDatabase.addChannel(person,channel);
	}
	public HashMap<Long, Long> getChannels(){
		return channelDatabase.getChannels();
	}
}