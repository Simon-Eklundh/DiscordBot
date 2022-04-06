import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.ArrayList;
import java.util.Arrays;

public enum Commands {

	HELP("help", "command for people who need help", null),

	THANKS("thanks", "command for people who have received help", null),

	LINK("link", "sends links to what is asked for", new ArrayList<>(Arrays.asList(SlashCommandOption.create(
					SlashCommandOptionType.SUB_COMMAND, "jdk", "shows liberica jdk"),
			SlashCommandOption.create(SlashCommandOptionType.SUB_COMMAND, "docker", "docker link"),
			SlashCommandOption.create(SlashCommandOptionType.SUB_COMMAND, "jdoc", "gets link to javadoc"))));








	private final String name;
	private final String description;
	private final ArrayList<SlashCommandOption> options;

	Commands(String name, String description, ArrayList<SlashCommandOption> options) {
		this.name = name;
		this.description = description;
		this.options = options;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}



	public ArrayList<SlashCommandOption> getOptions() {
		return options;
	}
}
