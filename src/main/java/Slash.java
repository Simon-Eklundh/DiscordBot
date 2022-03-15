import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.ChannelCategory;
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
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
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
		if(slashCommandInteraction.getCommandName().equals("help")){
			if (slashCommandInteraction.getServer().isPresent()) {
				Optional<Server> a = slashCommandInteraction.getServer();
				User b =slashCommandInteraction.getUser();

				if (!channels.containsKey(b.getId())) {
					try {
						ChannelCategory d = a.get().getChannelCategoriesByName("helpchannels").get(0);


						ServerTextChannelBuilder e = a.get().createTextChannelBuilder().setName("help-" + b.getName().toLowerCase(Locale.ROOT));
						e.setCategory(d);

						PermissionsBuilder p = new PermissionsBuilder();
						p.setAllowed(PermissionType.SEND_MESSAGES).setAllowed(PermissionType.READ_MESSAGES).setAllowed(PermissionType.READ_MESSAGE_HISTORY);



						CompletableFuture<ServerTextChannel> f = e.create();

						ServerTextChannel h = f.get();
						h.createUpdater().addPermissionOverwrite(b, p.build()).update();
						slashCommandInteraction.createImmediateResponder().setContent("join here" + h.getMentionTag()).respond();

						channels.put(b.getId(), h.getId());
						database.addChannel(b.getId(), h.getId());
					} catch (InterruptedException | ExecutionException | IndexOutOfBoundsException ex) {
						ex.printStackTrace();
					}

				} else {

					Optional<ServerTextChannel> x = api.getServerTextChannelById(channels.get(b.getId()));
					x.ifPresent(serverTextChannel -> slashCommandInteraction.createImmediateResponder().setContent("join here " + serverTextChannel.getMentionTag()));
				}
			}
		}
		else if(slashCommandInteraction.getCommandName().equals("thanks")){
			if (slashCommandInteraction.getServer().isPresent()) {
				Optional<Server> a = slashCommandInteraction.getServer();
				User b = slashCommandInteraction.getUser();
				if (channels.containsKey(b.getId())) {
					a.get().getChannelById(channels.get(b.getId())).get().delete();
					channels.remove(b.getId());
					database.removeChannel(b.getId());
				}

			}
		}
	}
}
