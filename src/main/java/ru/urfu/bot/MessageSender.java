package ru.urfu.bot;

import ru.urfu.bot.keyboards.IKeyboard;
import ru.urfu.discord.DiscordMessageSender;
import ru.urfu.profile.Profile;
import ru.urfu.telegram.TelegramMessageSender;

/**
 * Класс, отправляющий сообщения в разные источники
 */
public class MessageSender {
    /**
     * Отправляет простое сообщение
     * @param message текст
     * @param update апдейт куда отправлять
     */
    public static void sendMessage(String message, IUpdate update) {
        if (update.isFromTelegram()) {
            TelegramMessageSender.sendMessage(update.getMessage().getChatId().toString(), message);
        }
        else {
            DiscordMessageSender.sendMessage(update.getMessage().getChatId(), message);
        }
    }

    /**
     * Отправляет сообщение с кнопками
     * @param text текст
     * @param keyboard клавиатура
     * @param update апдейт куда отправлять
     */
    public static void sendMessageWithKeyboard(String text, IKeyboard keyboard, IUpdate update) {
        if (update.isFromTelegram()) {
            TelegramMessageSender.sendMessageWithKeyboard(update.getMessage().getChatId().toString(), text, keyboard.getTelegramKeyboard());
        } else {
            var discordKeyboard = keyboard.getDiscordKeyboard().getKeyboard();
            var result = String.format("%text\n%text", text, discordKeyboard);
            DiscordMessageSender.sendMessage(update.getMessage().getChatId(), result);
        }
    }

    /**
     * Отправляет фото с подписью
     * @param update апдейт куда отправлять
     * @param profile профиль, фото которого мы отправляем
     * @param caption подпись
     */
    public static void sendPhotoWithCaption(IUpdate update, Profile profile, String caption) {
        if (update.isFromTelegram()) {
            TelegramMessageSender.sendPhotoWithCaption(update, profile, caption);
        } else {
            DiscordMessageSender.sendPhoto(update.getMessage().getChatId(), caption, profile.getPhotoLink());
        }
    }
}
