package de.tomino.discordconsole.commands;

import de.tomino.discordconsole.DiscordConsole;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class SetTokenCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        System.out.println("SetTokenCommand");
        if (commandSender.hasPermission("discordconsole.setpreferences")) {
            System.out.println("Has permission");
            if (strings.length == 0) {
                commandSender.sendMessage("§cPlease specify a token!");
                System.out.println("No token");
                return true;
            }

            if (strings.length > 1) {
                commandSender.sendMessage("§cPlease specify only one token!");
                System.out.println("Too many tokens");
                return true;
            }
            DiscordConsole.config.setToken(strings[0]);
            commandSender.sendMessage("§aSuccessfully set the token to " + strings[0]);
            System.out.println("Successfully set token");
            return false;
        }
        return false;
    }
}

