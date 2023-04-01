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
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.apache.logging.log4j.LogManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.util.Objects;




public final class DiscordConsole extends JavaPlugin {

    private Logger appender;
    private static Plugin plugin;
    public static boolean enabled = true;

    public static Spark spark;
    public static Config config;

    public static boolean botRunning;

    @Override
    public void onEnable() {
        botRunning = false;
        plugin = this;
        spark = SparkProvider.get();
        config = new Config();


        String token = "MTA5MTQxODIyNjUyMjU4NzE5Ng.GIRjBA.OE9bI7OHOJp_XDTQVEtapwuKtVoSfcZW9AbOmc";
        String channelId = "1071100318357667975";
        String guildId = "1071100317921447936";

        if (config.getToken() == null || config.getLogChannelId() == null ||config.getGuildId() == null) {
            getLogger().severe("Please set the token, channel id and guild id in the config.yml or use the commands!");
        } else {
            try {
                Bot.startBot();
                botRunning = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        appender = new Logger(this, Bot.jda);
        try {
            final org.apache.logging.log4j.core.Logger logger = (org.apache.logging.log4j.core.Logger) LogManager.getRootLogger();
            logger.addAppender(appender);
        } catch (Exception e) {
            e.printStackTrace();
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
        botRunning = false;
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
