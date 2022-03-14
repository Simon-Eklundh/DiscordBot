import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.ChannelCategory;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.channel.ServerTextChannelBuilder;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.permission.PermissionsBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.HashMap;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class MessageCreateListener implements org.javacord.api.listener.message.MessageCreateListener {
	private final HashMap<Long, Long> channels;
	private final Database database;
	private final DiscordApi api;

	public MessageCreateListener(HashMap<Long, Long> channels, Database database, DiscordApi api) {
		this.channels = channels;
		this.database = database;
		this.api = api;
	}

	/**
	 * This method is called every time a message is created.
	 *
	 * @param event The event.
	 */
	@Override
	public void onMessageCreate(MessageCreateEvent event) {
		if (event.getMessageContent().equalsIgnoreCase("!help")) {
			if (event.getServer().isPresent() && event.getMessage().getUserAuthor().isPresent()) {
				Optional<Server> a = event.getServer();
				Optional<User> b = event.getMessage().getUserAuthor();

				if (!channels.containsKey(b.get().getId())) {
					try {
						ChannelCategory d = a.get().getChannelCategoriesByName("helpchannels").get(0);


						ServerTextChannelBuilder e = a.get().createTextChannelBuilder().setName("help-" + b.get().getName().toLowerCase(Locale.ROOT));
						e.setCategory(d);


						CompletableFuture<ServerTextChannel> f = e.create();

						ServerTextChannel h = f.get();
						event.getChannel().sendMessage("join here " + h.getMentionTag());
						channels.put(b.get().getId(), h.getId());
						database.addChannel(b.get().getId(), h.getId());
					} catch (InterruptedException | ExecutionException | IndexOutOfBoundsException ex) {
						ex.printStackTrace();
					}

				} else {

					Optional<ServerTextChannel> x = api.getServerTextChannelById(channels.get(b.get().getId()));
					x.ifPresent(serverTextChannel -> event.getChannel().sendMessage("join here " + serverTextChannel.getMentionTag()));
				}
			}


		} else if (event.getMessageContent().equalsIgnoreCase("!thanks")) {
			if (event.getServer().isPresent() && event.getMessage().getUserAuthor().isPresent()) {
				Optional<Server> a = event.getServer();
				Optional<User> b = event.getMessage().getUserAuthor();
				if (channels.containsKey(b.get().getId())) {
					a.get().getChannelById(channels.get(b.get().getId())).get().delete();
					database.removeChannel(b.get().getId(), channels.get(b.get().getId()));
				}

			}
		}
	}
}
