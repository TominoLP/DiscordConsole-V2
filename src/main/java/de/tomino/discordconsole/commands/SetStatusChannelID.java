package de.tomino.discordconsole.commands;

import de.tomino.discordconsole.DiscordConsole;
import de.tomino.discordconsole.discordbot.Bot;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class SetStatusChannelID implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender.hasPermission("discordconsole.setpreferences")) {
            if (strings.length == 0) {
                commandSender.sendMessage("§cPlease specify a channel ID!");
                return true;
            }

            if (strings.length > 1) {
                commandSender.sendMessage("§cPlease specify only one channel ID!");
                return true;
            }

            for (Guild guild : Bot.jda.getGuilds()) {
                TextChannel channel = guild.getTextChannelById(strings[0]);
                if (channel != null) {
                    commandSender.sendMessage("§aSuccessfully set the Guild to " + guild.getName() + " with the ID " + guild.getId());
                    commandSender.sendMessage("§aSuccessfully set the StatusChannel to " + channel.getName() + " with the ID " + channel.getId());
                    DiscordConsole.config.setGuildId(guild.getId());
                    DiscordConsole.config.setStatusChannelId(strings[0]);
                    if (DiscordConsole.botRunning)
                        Bot.sendMessage("§aSuccessfully set the Guild to " + guild.getName() + " with the ID " + guild.getId());
                    return true;
                }
            }

        }

        return false;
    }

}
