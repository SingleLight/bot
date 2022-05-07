package edu.northeastern.cs5500.starterbot.command;

import edu.northeastern.cs5500.starterbot.annotation.ExcludeFromJacocoGeneratedReport;
import edu.northeastern.cs5500.starterbot.controller.CatchController;
import edu.northeastern.cs5500.starterbot.controller.PositionController;
import edu.northeastern.cs5500.starterbot.model.MapNode;
import java.util.ArrayList;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.interactions.commands.CommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.components.selections.SelectionMenu;
import net.dv8tion.jda.api.interactions.components.selections.SelectionMenu.Builder;

@Singleton
@Slf4j
public class SeeWildPokemonsCommand implements Command {

    @Inject CatchController catchController;
    @Inject PositionController positionController;

    @Inject
    public SeeWildPokemonsCommand() {}

    @Override
    public String getName() {
        return "seewild";
    }

    @Override
    public CommandData getCommandData() {
        return new CommandData(getName(), "See the avaliable wild pokemons to catch");
    }

    @Override
    @ExcludeFromJacocoGeneratedReport
    public void onEvent(CommandInteraction event) {
        log.info("event: /seewild");
        String discordId = event.getUser().getId();
        MapNode currNode = positionController.getPlayerLocation(discordId);
        String locName = currNode.getName();
        ArrayList<String> wildPoke = catchController.seeWildPokemons(locName);

        Builder menu = createMenuBuilder(wildPoke);
        event.deferReply(true).addActionRow(menu.build()).queue();
    }

    /**
     * Create menu builder of wild pokemons
     *
     * @param wildPoke
     * @return Builder
     */
    public Builder createMenuBuilder(ArrayList<String> wildPoke) {
        Builder menu =
                SelectionMenu.create("menu:wildpokemons").setPlaceholder("Choose a wild pokemon");
        for (String string : wildPoke) {
            menu.addOption(string, string);
            if (menu.getOptions().size() >= 25) {
                break;
            }
        }
        return menu;
    }
}
