package at.jesus.goava.command;

import at.jesus.goava.Goava;
import discord4j.core.event.domain.message.MessageCreateEvent;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import lombok.var;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class CommandHandler {
    @Getter
    private final List<Command> commands;

    public CommandHandler() {
        commands = new ArrayList<>();

        val reflections = new Reflections(getClass().getPackage().getName());
        val classes = reflections.getSubTypesOf(Command.class);
        commands.addAll(classes.stream()
                .map(command -> {
                    try {
                        log.info("Initialized command {}}.", command.getName());
                        return command.newInstance();
                    } catch (InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect(Collectors.toList()));


        Goava.INSTANCE.getClient().getEventDispatcher().on(MessageCreateEvent.class)
                .subscribe(event -> {
                    var content = event.getMessage().getContent();

                    if (!content.startsWith(Goava.PREFIX)) return;
                    val args = content.replace(Goava.PREFIX, "").split(" ");

                    commands.stream()
                            .filter(command -> command.isValidName(args[0]))
                            .forEach(command -> command.execute(event.getMessage().getChannel().block(), args));
                });
    }
}
