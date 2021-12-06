package ru.urfu.bot;

import ru.urfu.bot.keyboards.IKeyboard;
import ru.urfu.discord.DiscordMessageSender;
import ru.urfu.profile.Profile;
import ru.urfu.telegram.TelegramMessageSender;

public class MessageSender {
    public static void sendMessage(String message, IUpdate update) {
        if (update.isFromTelegram()) {
            TelegramMessageSender.sendMessage(update.getMessage().getChatId().toString(), message);
        }
        else {
            DiscordMessageSender.sendMessage(update.getMessage().getChatId(), message);
        }
    }

    public static void sendMessageWithKeyboard(String s, IKeyboard keyboard, IUpdate update) {
        if (update.isFromTelegram()) {
            TelegramMessageSender.sendMessageWithKeyboard(update.getMessage().getChatId().toString(), s, keyboard.getTelegramKeyboard());
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
