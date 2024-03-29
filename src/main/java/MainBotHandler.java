import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.interaction.SlashCommand;

import java.util.HashMap;

public class MainBotHandler {
    //todo uncomment for when it's actually used
    private static final Database database = null;

    public MainBotHandler() {
        //discord bot token
        String token = Credentials.getTEST_TOKEN();

        DiscordApi api = new DiscordApiBuilder().setToken("pt4Mx-KLXTejok-w6uGULf8xghVInXq5").login().join();


        // database on or off todo (uncomment when using the database again
        //HashMap<Long, Long> textChannelPerPerson = database.getChannels();
        HashMap<Long, Long> textChannelPerPerson = new HashMap<>();


        InstantiateSlashCommands(api);

        AddListeners(api, textChannelPerPerson);
        // Print the invite url of your bot
        System.out.println("You can invite the bot by using the following url: " + api.createBotInvite());

    }

    private void InstantiateSlashCommands(DiscordApi api) {
        SlashCommand.with(Commands.HELP.getName(), Commands.HELP.getDescription()).createGlobal(api).join();
        SlashCommand.with(Commands.THANKS.getName(), Commands.THANKS.getDescription()).createGlobal(api).join();
        SlashCommand.with(Commands.LINK.getName(), Commands.LINK.getDescription(), Commands.LINK.getOptions()).createGlobal(api).join();
        //SlashCommand.with(Commands.REMIND.getName(), Commands.REMIND.getDescription(), Commands.REMIND.getOptions()).createGlobal(api).join();
    }

    private void AddListeners(DiscordApi api, HashMap<Long, Long> textChannelPerPerson) {
        api.addSlashCommandCreateListener(new Slash(textChannelPerPerson, database, api));
    }


}
