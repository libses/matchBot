package ru.urfu.bot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import ru.urfu.profile.Profile;

public class MessageSender {
    public static void sendMessage(String chatIdFromUpdate, String s, ReplyKeyboardMarkup main, IUpdate update) {
        if (update.isFromTelegram()) {
            TelegramMessageSender.sendMessage(chatIdFromUpdate, s, main);
        }
        else {
            DiscordMessageSender.sendMessage(update.getMessage().getChatId(), s);
        }
    }

    public static void sendPhotoWithCaption(IUpdate update, Profile p, String caption) {
        if (update.isFromTelegram()) {
            TelegramMessageSender.sendPhotoWithCaption(update, p, caption);
        }
        else {
            DiscordMessageSender.sendPhoto(update.getMessage().getChatId(), caption, p.getPhotoLink());
        }
    }
}
