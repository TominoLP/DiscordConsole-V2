package de.tomino.discordconsole.discordbot;

import de.tomino.discordconsole.DiscordConsole;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class DiscordEvents extends ListenerAdapter {

    public static boolean ready = false;
    public static boolean enabled = true;

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if (e.getAuthor() == e.getJDA().getSelfUser()) {
            return;
        }

        if (e.getMessage().getContentRaw().startsWith("#")) {
            return;
        }

        if (enabled) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), e.getMessage().getContentRaw());
                }
            }.runTask(DiscordConsole.getPlugin(DiscordConsole.class));
        }
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        super.onReady(event);
        Bukkit.getLogger().info("Discord Bot is ready!");
        ready = true;

        for (EmbedBuilder em : Bot.QueueEmb) {
            Bot.sendEmbed(em);
        }
        Bot.QueueEmb.clear();

        for (String s : Bot.QueueMes) {
            Bot.sendMessage(s);
        }
        Bot.QueueMes.clear();
    }
}
