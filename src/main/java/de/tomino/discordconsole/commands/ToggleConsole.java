package de.tomino.discordconsole.commands;

import de.tomino.discordconsole.DiscordConsole;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ToggleConsole implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player player) {
            if (!player.hasPermission("discordconsole.toggle")) {
                player.sendMessage("§cYou don't have permission to do that!");
                return true;
            }
            if (strings.length == 0) {
                player.sendMessage("§cUsage: /toggleconsole <on/off>");
                return true;
            }
            if (strings[0].equalsIgnoreCase("on")) {
                DiscordConsole.enabled = true;
                player.sendMessage("§aEnabled console!");
                return true;
            }
            if (strings[0].equalsIgnoreCase("off")) {
                DiscordConsole.enabled = false;
                player.sendMessage("§cDisabled console!");
                return true;
            }

        }

        return false;
    }
}
