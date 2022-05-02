package edu.northeastern.cs5500.starterbot.command;

import edu.northeastern.cs5500.starterbot.controller.PlayerController;
import edu.northeastern.cs5500.starterbot.controller.PositionController;
import edu.northeastern.cs5500.starterbot.model.MapNode;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.interactions.commands.CommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.components.selections.SelectionMenu;
import net.dv8tion.jda.api.interactions.components.selections.SelectionMenu.Builder;

@Singleton
@Slf4j
public class MovePositionCommand implements Command {

    @Inject PlayerController playerController;
    @Inject PositionController positionController;

    @Inject
    public MovePositionCommand() {}

    @Override
    public String getName() {
        return "moveposition";
    }

    @Override
    public CommandData getCommandData() {
        return new CommandData(getName(), "Move player to another location");
    }

    @Override
    public void onEvent(CommandInteraction event) {
        log.info("event: /move");
        String discordId = event.getUser().getId();
        MapNode currNode = positionController.getPlayerLocation(discordId);
        String locName = currNode.getName();
        String locPic = currNode.getImageUrl();
        List<MapNode> neighbors = positionController.getPossibleMovesForUser(discordId);
        ArrayList<String> neighborName = new ArrayList<>(20);

        // get all neighbor name for options
        for (MapNode neighbor : neighbors) {
            neighborName.add(neighbor.getName());
            log.info("neighbor:" + neighbor.getName());
        }

        // show location name and picture
        EmbedBuilder info = new EmbedBuilder();
        info.setTitle("Location Infomation");
        info.addField("Current Location: ", locName, false);
        info.setImage(locPic);
        info.setColor(0xf45642);

        Builder menu =
                SelectionMenu.create("menu:nextnode").setPlaceholder("Choose your next node");
        for (MapNode neighbor : neighbors) {
            menu.addOption(neighbor.getName(), String.valueOf(neighbor.getLocation()));
        }
        event.deferReply(true).addActionRow(menu.build()).addEmbeds(info.build()).queue();
    }
}
