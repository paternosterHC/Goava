package at.jesus.goava.command.commands;

import at.jesus.goava.Goava;
import at.jesus.goava.command.Command;
import at.jesus.goava.command.CommandData;
import discord4j.core.object.entity.channel.MessageChannel;
import lombok.val;

@CommandData(names = {"help"}, syntax = "help")
public class HelpCommand extends Command {

    @Override
    public void execute(MessageChannel channel, String[] args) {
        val stringBuilder = new StringBuilder();
        Goava.INSTANCE.getCommandHandler().getCommands().forEach(command -> {
            stringBuilder.append("[] ");
            stringBuilder.append(Goava.PREFIX);
            stringBuilder.append(command.getSyntax());
            stringBuilder.append(System.lineSeparator());
        });

        sendChatMessage(channel, stringBuilder.toString());
    }
}
