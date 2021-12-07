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
     * Поле для тестов и возможного расширения
     */
    public static String currentMessage;

    /**
     * Отправляет простое сообщение
     * @param message текст
     * @param update апдейт куда отправлять
     */
    public static void sendMessage(String message, IUpdate update) {
        if (update.getUpdateSource() == UpdateSource.Telegram) {
            TelegramMessageSender.sendMessage(update.getMessage().getChatId().toString(), message);
        }
        else if (update.getUpdateSource() == UpdateSource.Discord){
            DiscordMessageSender.sendMessage(update.getMessage().getChatId(), message);
        }
        currentMessage = message;
    }

    /**
     * Отправляет сообщение с кнопками
     * @param text текст
     * @param keyboard клавиатура
     * @param update апдейт куда отправлять
     */
    public static void sendMessageWithKeyboard(String text, IKeyboard keyboard, IUpdate update) {
        if (update.getUpdateSource() == UpdateSource.Telegram) {
            TelegramMessageSender.sendMessageWithKeyboard(update.getMessage().getChatId().toString(), text, keyboard.getTelegramKeyboard());
        } else if (update.getUpdateSource() == UpdateSource.Discord){
            var discordKeyboard = keyboard.getDiscordKeyboard().getKeyboard();
            var result = text + '\n' + discordKeyboard;
            DiscordMessageSender.sendMessage(update.getMessage().getChatId(), result);
        }
        currentMessage = text;
    }

    /**
     * Отправляет фото с подписью
     * @param update апдейт куда отправлять
     * @param profile профиль, фото которого мы отправляем
     * @param caption подпись
     */
    public static void sendPhotoWithCaption(IUpdate update, Profile profile, String caption) {
        if (update.getUpdateSource() == UpdateSource.Telegram) {
            TelegramMessageSender.sendPhotoWithCaption(update, profile, caption);
        } else if (update.getUpdateSource() == UpdateSource.Discord) {
            DiscordMessageSender.sendPhoto(update.getMessage().getChatId(), caption, profile.getPhotoLink());

            DiscordMessageSender.sendMessage(update.getMessage().getChatId(),
                    profile.getCurrentKeyboard().getDiscordKeyboard().getKeyboard());
        }
        currentMessage = caption;
    }
}
