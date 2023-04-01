package de.tomino.discordconsole;


import de.tomino.discordconsole.commands.ToggleConsole;
import de.tomino.discordconsole.discordbot.Bot;
import de.tomino.discordconsole.utils.Logger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.apache.logging.log4j.LogManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.util.Objects;




public final class DiscordConsole extends JavaPlugin {

    public static final String channelId = "1071100318357667975";
    public static final String guildId = "1071100317921447936";
    public static final String token = "MTA5MTQxODIyNjUyMjU4NzE5Ng.GJhPuA.YjyRkII6ULubdy0DOiiueRszVmHa6LGRxn2IEU";
    private Logger appender;
    private static Plugin plugin;
    public static boolean enabled = true;

    @Override
    public void onEnable() {


        plugin = this;
        try {
            Bot.startBot();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (enabled) {
            appender = new Logger(this, Bot.jda);
            try {
                final org.apache.logging.log4j.core.Logger logger = (org.apache.logging.log4j.core.Logger) LogManager.getRootLogger();
                logger.addAppender(appender);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
        Objects.requireNonNull(getCommand("toggleDC")).setExecutor(new ToggleConsole());

        EmbedBuilder startEmbed = new EmbedBuilder();
        startEmbed.setTitle("Server");
        startEmbed.setDescription("STARTED");
        startEmbed.setColor(Color.GREEN);
        Bot.sendEmbed(startEmbed);

    }

    @Override
    public void onDisable() {
        final org.apache.logging.log4j.core.Logger logger = (org.apache.logging.log4j.core.Logger) LogManager.getRootLogger();
        logger.removeAppender(getAppender());
        getAppender().stop();

        EmbedBuilder stopEmbed = new EmbedBuilder();
        stopEmbed.setTitle("Server");
        stopEmbed.setDescription("STOPPED");
        stopEmbed.setColor(Color.RED);
        Bot.sendEmbed(stopEmbed);
        // Plugin shutdown logic
    }

    public Logger getAppender() {
        return appender;
    }

    public static Plugin getPlugin() {
        return plugin;
    }
}
