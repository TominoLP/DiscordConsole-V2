package de.tomino.discordconsole.utils;

import de.tomino.discordconsole.DiscordConsole;
import de.tomino.discordconsole.discordbot.Bot;
import net.dv8tion.jda.api.JDA;
import org.apache.logging.log4j.core.LogEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;

import java.util.Calendar;
import java.util.Locale;

public class Logger extends AbstractAppender {

    public static Calendar cal = Calendar.getInstance(Locale.GERMANY);
    public static String time = String.format("%02d:%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));

    public Logger(DiscordConsole plugin, JDA j) {
        super("MyAppender", null, null);
        start();
    }

    @Override
    public void append(LogEvent event) {
        if (!DiscordConsole.botRunning) return;
        String message = event.getMessage().getFormattedMessage();
        message = "[" + time + " " + event.getLevel().toString() + "]: " + message.replace("§", "") + "\n";
        Bot.sendMessage(message);
    }

    public static class CLogger implements Listener {

        @EventHandler
        public void onServerCommand(ServerCommandEvent event) {
            if (!DiscordConsole.botRunning) return;
            String command = event.getCommand();
            command = "[" + time + " COM]: Console: /" + command + "\n";
            Bot.sendMessage(command);
        }

        @EventHandler
        public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
            if (!DiscordConsole.botRunning) return;
            String command = event.getMessage();
            command = "[" + time + " COM]: " + event.getPlayer().getName() + ": " + command + "\n";
            Bot.sendMessage(command);
        }

        @EventHandler
        public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
            if (!DiscordConsole.botRunning) return;
            String message = event.getMessage();
            message = "[" + time + " MSG]: " + event.getPlayer().getName() + ": " + message + "\n";
            Bot.sendMessage(message);

        }

    }

}

