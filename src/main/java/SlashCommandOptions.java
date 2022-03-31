import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum SlashCommandOptions {

    LINK_OPTIONS(new ArrayList<>(Arrays.asList(SlashCommandOption.create(
            SlashCommandOptionType.SUB_COMMAND, "jdk", "shows liberica jdk"),
            SlashCommandOption.create(SlashCommandOptionType.SUB_COMMAND, "docker", "docker link"),
            SlashCommandOption.create(SlashCommandOptionType.SUB_COMMAND, "jdoc", "gets link to javadoc"))));

    private ArrayList<SlashCommandOption> options;

    SlashCommandOptions(ArrayList<SlashCommandOption> options) {
        this.options = options;
    }

    public List<SlashCommandOption> getOptions() {
        return options;
    }

}
