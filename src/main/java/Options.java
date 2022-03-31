import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.ArrayList;
import java.util.List;

public class Options {
    private static List<SlashCommandOption> linkOptions;


    public static List<SlashCommandOption> getLinkOptions() {
        if (linkOptions != null) return linkOptions;

        //todo create linkOptions and add them here
        linkOptions = new ArrayList<>();
        linkOptions.add(SlashCommandOption.create(SlashCommandOptionType.SUB_COMMAND, "jdk", "gives a link to liberica jdk"));
        linkOptions.add(SlashCommandOption.create(SlashCommandOptionType.SUB_COMMAND, "docker", "gives a link to docker download page"));
        linkOptions.add(SlashCommandOption.create(SlashCommandOptionType.SUB_COMMAND, "jdoc", "gives a java doc link"));


       /*
       this would be impossible without knowing what the javadoc is under

       linkOptions.add(SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "jdoc",
                "gets a javadoc link to what is searched for hopefully", new SlashCommandOptionBuilder().addOption(SlashCommandOption.createStringOption("section", "section of javadoc" ,false))));
*/

        return linkOptions;
    }
}
