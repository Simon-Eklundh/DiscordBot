import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.ChannelCategory;
import org.javacord.api.entity.channel.ServerChannel;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.channel.ServerTextChannelBuilder;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.permission.PermissionsBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;

import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class Slash implements SlashCommandCreateListener {
	private final HashMap<Long, Long> channels;
	private final Database database;
	private final DiscordApi api;

	public Slash(HashMap<Long, Long> channels, Database database, DiscordApi api) {
		this.channels = channels;
		this.database = database;
		this.api = api;
	}

	/**
	 * This method is called every time an interaction is created.
	 *
	 * @param event The event.
	 */
	@Override
	public void onSlashCommandCreate(SlashCommandCreateEvent event) {
		SlashCommandInteraction slashCommandInteraction = event.getSlashCommandInteraction();

		switch (slashCommandInteraction.getCommandName()){
			case "help":
				handleHelpCommand(slashCommandInteraction);
				break;
			case "thanks":
				handleThanksCommand(slashCommandInteraction);
				break;
			case "link":
				handleLinkCommand(slashCommandInteraction);
				break;
			case "remind":

			default:
				break;
		}

	}

	private void handleLinkCommand(SlashCommandInteraction slashCommandInteraction) {
		if (slashCommandInteraction.getServer().isPresent()) {
			if(slashCommandInteraction.getOptions().isEmpty()) return;


			switch (slashCommandInteraction.getOptions().get(0).getName()){
				case "jdoc":
					slashCommandInteraction.createImmediateResponder().appendNamedLink("javadoc", "https://docs.oracle.com/en/java/javase/17/docs/api/index.html").respond();
					break;
				case "docker":
					slashCommandInteraction.createImmediateResponder().appendNamedLink("docker", "https://hub.docker.com/").respond();
					break;
				case "jdk":
					slashCommandInteraction.createImmediateResponder().appendNamedLink("liberica", "https://bell-sw.com/pages/downloads/#/java-17-lts").respond();
					break;
				default:
					break;
			}
		}
	}

	private void handleThanksCommand(SlashCommandInteraction slashCommandInteraction) {
		if (slashCommandInteraction.getServer().isPresent()) {
			Server server = slashCommandInteraction.getServer().get();
			Long userID = slashCommandInteraction.getUser().getId();
			if (channels.containsKey(userID)) {
				server.getChannelById(channels.get(userID)).ifPresent(ServerChannel::delete);
				channels.remove(userID);
				database.removeChannel(userID);
				slashCommandInteraction.createImmediateResponder().setContent("glad you liked it").respond();
				return;
			}
			slashCommandInteraction.createImmediateResponder().setContent("No channel to remove, glad you're happy though").respond();

		}
	}

	private void handleHelpCommand(SlashCommandInteraction slashCommandInteraction) {
		if (slashCommandInteraction.getServer().isPresent()) {
			Server server = slashCommandInteraction.getServer().get();
			User user = slashCommandInteraction.getUser();

			if (!channels.containsKey(user.getId())) {
				try {
					ChannelCategory channelCategory = server.getChannelCategoriesByName("helpchannels").get(0);

					ServerTextChannelBuilder textChannelBuilder = server.createTextChannelBuilder().setName("help-" + user.getName().toLowerCase(Locale.ROOT));
					textChannelBuilder.setCategory(channelCategory);

					PermissionsBuilder permissionsBuilder = new PermissionsBuilder();
					permissionsBuilder.setAllowed(PermissionType.SEND_MESSAGES).setAllowed(PermissionType.READ_MESSAGES).setAllowed(PermissionType.READ_MESSAGE_HISTORY);


					ServerTextChannel serverTextChannel = textChannelBuilder.create().get();


					serverTextChannel.createUpdater().addPermissionOverwrite(user, permissionsBuilder.build()).update();
					slashCommandInteraction.createImmediateResponder().setContent("join here" + serverTextChannel.getMentionTag()).respond();

					channels.put(user.getId(), serverTextChannel.getId());
					database.addChannel(user.getId(), serverTextChannel.getId());

				} catch (InterruptedException | ExecutionException | IndexOutOfBoundsException ex) {
					ex.printStackTrace();
				}

			} else {
				if (api.getTextChannelById(channels.get(user.getId())).isPresent()) {
					ServerTextChannel serverTextChannel = api.getServerTextChannelById(channels.get(user.getId())).get();
					slashCommandInteraction.createImmediateResponder().setContent("join here " + serverTextChannel.getMentionTag()).respond();
				} else {

					channels.remove(user.getId());
					database.removeChannel(user.getId());
					handleHelpCommand(slashCommandInteraction);

				}
			}
		}
	}


}
