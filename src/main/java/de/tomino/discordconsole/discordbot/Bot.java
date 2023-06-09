package de.tomino.discordconsole.discordbot;


import de.tomino.discordconsole.DiscordConsole;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.ArrayList;
import java.util.Objects;

public class Bot {

    public static ArrayList<String> QueueMes = new ArrayList<>();
    public static ArrayList<EmbedBuilder> QueueEmb = new ArrayList<>();
    public static JDA jda;

    public static void startBot() throws InterruptedException {
        jda = JDABuilder
                .createDefault(DiscordConsole.config.getToken())
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .build();

        jda.addEventListener(new DiscordEvents());
        DiscordConsole.botRunning = true;

    }

    /**
     * Sends a Message to the Discord Channel
     *
     * @param message String
     */
    public static void sendMessage(String message) {
        if (!DiscordConsole.botRunning) return;
        if (!(DiscordEvents.ready)) {
            QueueMes.add(message);
        } else {
            Objects.requireNonNull(Objects.requireNonNull(jda.getGuildById(DiscordConsole.config.getGuildId()))
                    .getTextChannelById(DiscordConsole.config.getLogChannelId())).sendMessage(message).queue();
            message = "";
        }
    }

    /**
     * Sends an Embed to the Discord Channel
     *
     * @param embed EmbedBuilder
     */
    public static void sendEmbed(EmbedBuilder embed) {
        if (!DiscordConsole.botRunning) return;
        if (!(DiscordEvents.ready)) {
            QueueEmb.add(embed);
        } else {
            Objects.requireNonNull(Objects.requireNonNull(jda.getGuildById(DiscordConsole.config.getGuildId()))
                    .getTextChannelById(DiscordConsole.config.getLogChannelId())).sendMessageEmbeds(embed.build()).queue();
            embed.clear();

        }
    }

}
