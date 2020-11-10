package at.jesus.goava;

import at.jesus.goava.command.CommandHandler;
import at.jesus.goava.file.FileHandler;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.discordjson.json.gateway.StatusUpdate;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
public class Goava {
    public static final String PREFIX = "g!";
    public static final Goava INSTANCE = new Goava();

    @Getter
    private boolean running = true;

    @Getter
    private GatewayDiscordClient client;

    @Getter
    private CommandHandler commandHandler;

    @Getter
    private FileHandler fileHandler;

    public void init(String token) throws IOException {
        // initialize discord client & log in
        client = DiscordClientBuilder
                .create(token)
                .build()
                .login()
                .block();

        // load data
        fileHandler = new FileHandler();

        // load commands
        commandHandler = new CommandHandler();

        // presence
        resetPresence();

        // prepare for shutdown
        client.onDisconnect().block();

        // save data
        running = false;
        fileHandler.save();
    }

    public void resetPresence() {
        client.updatePresence(StatusUpdate.builder().status(PREFIX + "help").afk(false).build());
    }
}
