package at.jesus.goava.command.commands;

import at.jesus.goava.command.Command;
import at.jesus.goava.command.CommandData;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.channel.MessageChannel;

@CommandData(names = {"test"}, syntax = "test")
public class TestCommand extends Command {
    @Override
    public void execute(MessageChannel channel, String[] args) {
        channel.createMessage("Test!").block();
    }
}
