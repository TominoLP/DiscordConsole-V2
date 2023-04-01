package de.tomino.discordconsole.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import de.tomino.discordconsole.discordbot.Bot;
import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.Yaml;

public class Config {

    private File configFile = new File("plugins/DiscordConsole/config.yml");
    private YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
    private String token;
    private String guildId;
    private String logChannelId;
    private String statusChannelId;
    private boolean consoleLog;

    public Config() {
        load();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        config.set("token", token);
        this.token = token;
        save();
        try {
            Bot.startBot();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guildId) {
        config.set("guildId", guildId);
        this.guildId = guildId;
        save();
    }

    public String getLogChannelId() {
        return logChannelId;
    }

    public void setLogChannelId(String logChannelId) {
        this.logChannelId = logChannelId;
        config.set("logChannelId", logChannelId);
        save();
    }

    public String getStatusChannelId() {
        return statusChannelId;
    }

    public void setStatusChannelId(String statusChannelId) {
        config.set("statusChannelId", statusChannelId);
        this.statusChannelId = statusChannelId;
        save();
    }

    public boolean isConsoleLog() {
        return consoleLog;
    }

    public void setConsoleLog(boolean consoleLog) {
        config.set("consoleLog", consoleLog);
        this.consoleLog = consoleLog;
        save();
    }

    private void load() {

        System.out.println("Loading config..." + configFile.getAbsolutePath());

        if (config.get("token") != null) {
            this.token = config.getString("token");
        }
        if (config.get("guildId") != null) {
            this.guildId = config.getString("guildId");
        }
        if (config.get("logChannelId") != null) {
            this.logChannelId = config.getString("logChannelId");
        }
        if (config.get("statusChannelId") != null) {
            this.statusChannelId = config.getString("statusChannelId");
        }
        if (config.get("consoleLog") != null) {
            this.consoleLog = config.getBoolean("consoleLog");
        }
        save();

    }

    private void save() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
