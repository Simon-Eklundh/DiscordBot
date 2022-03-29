import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.interaction.SlashCommand;

import java.util.HashMap;

public class MainBotHandler {

	private static final Database database = new Database();

	public MainBotHandler() {
		//discord bot token
		String token = SecretStrings.TOKEN.getValue();

		DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();

		HashMap<Long, Long> textChannelPerPerson = database.getChannels();
		//HashMap<Long, Long> textChannelPerPerson = new HashMap<>();
		InstantiateSlashCommands(api);

		AddListeners(api, textChannelPerPerson);
		// Print the invite url of your bot
		System.out.println("You can invite the bot by using the following url: " + api.createBotInvite());
	}

	private void InstantiateSlashCommands(DiscordApi api) {
		SlashCommand.with("help", "command for people who need help").createGlobal(api).join();
		SlashCommand.with("thanks", "command for people who have received help").createGlobal(api).join();
	}

	private void AddListeners(DiscordApi api, HashMap<Long, Long> textChannelPerPerson) {
		api.addSlashCommandCreateListener(new Slash(textChannelPerPerson, database, api));
	}


}
