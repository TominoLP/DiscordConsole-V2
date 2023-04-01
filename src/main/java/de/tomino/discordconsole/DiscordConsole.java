package de.tomino.discordconsole;


import de.tomino.discordconsole.commands.SetLogChannelID;
import de.tomino.discordconsole.commands.SetTokenCommand;
import de.tomino.discordconsole.commands.ToggleConsole;
import de.tomino.discordconsole.discordbot.Bot;
import de.tomino.discordconsole.discordbot.StatusEmbed;
import de.tomino.discordconsole.utils.Config;
import de.tomino.discordconsole.utils.Logger;
import me.lucko.spark.api.Spark;
import me.lucko.spark.api.SparkProvider;
import net.dv8tion.jda.api.EmbedBuilder;
import org.apache.logging.log4j.LogManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.util.Objects;


public final class DiscordConsole extends JavaPlugin {

    public static boolean enabled = true;
    public static Spark spark;
    public static Config config;
    public static boolean botRunning;
    private static Plugin plugin;
    private Logger appender;

    public static Plugin getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        botRunning = false;
        plugin = this;
        spark = SparkProvider.get();
        config = new Config();


        if (config.getToken() == null || config.getLogChannelId() == null || config.getGuildId() == null) {
            getLogger().severe("Please set the token, channel id and guild id in the config.yml or use the commands!");
        } else {
            try {
                Bot.startBot();
                botRunning = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (config.isConsoleLog()) {
            appender = new Logger(this, Bot.jda);
            try {
                final org.apache.logging.log4j.core.Logger logger = (org.apache.logging.log4j.core.Logger) LogManager.getRootLogger();
                logger.addAppender(appender);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            getLogger().warning("Console logging is disabled!");
        }

        Objects.requireNonNull(getCommand("toggledc")).setExecutor(new ToggleConsole());
        Objects.requireNonNull(getCommand("settoken")).setExecutor(new SetTokenCommand());
        Objects.requireNonNull(getCommand("setlogchannel")).setExecutor(new SetLogChannelID());


        if (botRunning) {
            EmbedBuilder startEmbed = new EmbedBuilder();
            startEmbed.setTitle("Server");
            startEmbed.setDescription("STARTED");
            startEmbed.setColor(Color.GREEN);
            Bot.sendEmbed(startEmbed);
            StatusEmbed.sendFirst();
        } else {
            getLogger().warning("Bot is not running! skipping status embed");
        }

    }

    @Override
    public void onDisable() {
        if (botRunning) {
            EmbedBuilder stopEmbed = new EmbedBuilder();
            stopEmbed.setTitle("Server");
            stopEmbed.setDescription("STOPPED");
            stopEmbed.setColor(Color.RED);
            Bot.sendEmbed(stopEmbed);
        } else {
            getLogger().warning("Bot is not running! skipping status embed");
        }

        final org.apache.logging.log4j.core.Logger logger = (org.apache.logging.log4j.core.Logger) LogManager.getRootLogger();
        logger.removeAppender(getAppender());
        getAppender().stop();


        // Plugin shutdown logic
        botRunning = false;
    }

    public Logger getAppender() {
        return appender;
    }
}
