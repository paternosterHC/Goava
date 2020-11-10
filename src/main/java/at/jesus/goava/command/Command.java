package at.jesus.goava.command;

import at.jesus.goava.Goava;
import discord4j.core.object.entity.channel.MessageChannel;
import lombok.Data;

import java.util.Arrays;

@Data
public abstract class Command implements Executable {
    private String[] names;
    private String description, syntax;

    public Command() {
        CommandData commandData = getClass().getAnnotation(CommandData.class);
        this.names = commandData.names();
        this.description = commandData.description();
        this.syntax = commandData.syntax();
    }

    public void sendSyntax(MessageChannel channel) {
        sendChatMessage(channel, "Syntax: " + Goava.PREFIX + getSyntax());
    }

    public void sendChatMessage(MessageChannel channel, String message, String... format) {
        channel.createMessage(String.format(message, format)).block();
    }

    public boolean isValidName(String name) {
        return Arrays.stream(names).anyMatch(name1 -> name1.equalsIgnoreCase(name));
    }
}

