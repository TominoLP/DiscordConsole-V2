package de.tomino.discordconsole.discordbot;

import de.tomino.discordconsole.DiscordConsole;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class StatusEmbed {

    private final ScheduledExecutorService scheduler;
    private final TextChannel channel;
    private final EmbedBuilder embedBuilder;

    public StatusEmbed(TextChannel channel, EmbedBuilder embedBuilder) {
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        this.channel = channel;
        this.embedBuilder = embedBuilder;
    }

    public void start() {
        scheduler.scheduleAtFixedRate(() -> {
            channel.editMessageById("message_id", (CharSequence) embedBuilder.build()).queue();
        }, 0, 10, TimeUnit.SECONDS);
    }

    public void stop() {
        scheduler.shutdown();
    }
    public static void sendFirst(){
        if (!DiscordConsole.botRunning) return;
        if (DiscordConsole.config.getStatusChannelId() == null) return;
        TextChannel channel = Bot.jda.getTextChannelById(DiscordConsole.config.getStatusChannelId());
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Server Status");
        embedBuilder.setDescription("Server is starting...");
        embedBuilder.setColor(0x00ff00);
        Bot.sendEmbed(embedBuilder);

        StatusEmbed statusEmbed = new StatusEmbed(channel, embedBuilder);
        statusEmbed.start();

    }


}
