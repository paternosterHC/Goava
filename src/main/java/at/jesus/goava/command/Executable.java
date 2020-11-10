package at.jesus.goava.command;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.channel.MessageChannel;

public interface Executable {
    void execute(MessageChannel channel, String[] args);
}
