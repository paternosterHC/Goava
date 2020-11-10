package at.jesus.goava.command.commands;

import at.jesus.goava.Goava;
import at.jesus.goava.command.Command;
import at.jesus.goava.command.CommandData;
import at.jesus.goava.util.FormatUtil;
import at.jesus.goava.util.Timer;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.discordjson.json.gateway.StatusUpdate;
import lombok.val;

@CommandData(names = {"talk", "t"}, syntax = "talk <start/stop>")
public class TalkCommand extends Command {
    private TalkType currentType = TalkType.STOP;
    private Timer timer = new Timer();

    @Override
    public void execute(MessageChannel channel, String[] args) {
        if (args.length != 2) {
            sendSyntax(channel);
            return;
        }

        try {
            val type = TalkType.valueOf(args[1].toUpperCase());
            switch (type) {
                case START:
                    if (currentType.equals(TalkType.START))
                        sendChatMessage(channel,"Er redet bereits seit %s :)", timer.format());
                    else {
                        timer.start();

                        // activity thread
                        // TODO
                        val activityThread = new Thread(() -> {
                            do {
                                Goava.INSTANCE.getClient().updatePresence(StatusUpdate.builder().status(timer.format()).afk(false).build());
                            } while (currentType.equals(TalkType.START));
                            Goava.INSTANCE.resetPresence();
                        });
//                        activityThread.start();

                        sendChatMessage(channel, "Er hat zum %sten mal angefangen zu reden...", String.valueOf(Goava.INSTANCE.getFileHandler().getSaveFile().increment()));
                    }
                    break;
                case STOP:
                    if (currentType.equals(TalkType.STOP))
                        sendChatMessage(channel,"Er redet gar nicht?!");
                    else {
                        val total = Goava.INSTANCE.getFileHandler().getSaveFile().addTime(timer.getDifference());
                        sendChatMessage(channel, "Er hat nach %s aufgehört zu reden! (insgesamt: %s)", timer.format(), FormatUtil.msToString(total));
                    }
                    break;
            }

            currentType = type;
        } catch (IllegalArgumentException e) {
            sendSyntax(channel);
        }
    }

    enum TalkType {
        START, STOP;
    }
}
