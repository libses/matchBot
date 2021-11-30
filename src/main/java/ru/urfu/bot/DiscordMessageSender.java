package ru.urfu.bot;

import net.dv8tion.jda.api.JDA;

public class DiscordMessageSender {
    public static JDA api;

    public static void sendMessage(Long chatId, String text){
        api.getTextChannelById(chatId).sendMessage(text).queue();
    }
}
