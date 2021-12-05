package ru.urfu.bot;

import ru.urfu.profile.Profile;

public class MessageSender {
    public static void sendMessage(String chatIdFromUpdate, String message, IUpdate update) {
        if (update.isFromTelegram()) {
            TelegramMessageSender.sendMessage(chatIdFromUpdate, message);
        }
        else {
            DiscordMessageSender.sendMessage(update.getMessage().getChatId(), message);
        }
    }

    public static void sendMessageWithKeyboard(String chatIdFromUpdate, String s, IKeyboard keyboard, IUpdate update) {
        if (update.isFromTelegram()) {
            TelegramMessageSender.sendMessageWithKeyboard(chatIdFromUpdate, s, keyboard.getTelegramKeyboard());
        } else {
            var discordKeyboard = keyboard.getDiscordKeyboard().getKeyboard();
            var result = String.format("%s\n%s", s, discordKeyboard);
            DiscordMessageSender.sendMessage(update.getMessage().getChatId(), result);
        }
    }

    public static void sendPhotoWithCaption(IUpdate update, Profile p, String caption) {
        if (update.isFromTelegram()) {
            TelegramMessageSender.sendPhotoWithCaption(update, p, caption);
        } else {
            DiscordMessageSender.sendPhoto(update.getMessage().getChatId(), caption, p.getPhotoLink());
        }
    }
}
