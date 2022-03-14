import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import java.util.HashMap;

public class Main {

private static HashMap<Long,Long> textChannelPerPerson = new HashMap<>();
private static Database database = new Database();


	public static void main(String[] args) {
		// Insert your bot's token here


		String token = SecretStrings.getToken();

		DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();

		//textChannelPerPerson = database.getChannels();
		api.addMessageCreateListener(new MessageCreateListener(textChannelPerPerson, database, api));


		// Print the invite url of your bot
		System.out.println("You can invite the bot by using the following url: " + api.createBotInvite());
	}

}
