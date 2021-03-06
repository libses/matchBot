package ru.urfu.telegram;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.urfu.bot.IUpdate;
import ru.urfu.bot.keyboards.Keyboards;
import ru.urfu.profile.Profile;

/**
 * Класс, который отправляет сообщения в телеграмм
 */
public class TelegramMessageSender {
    public static TelegramBot bot;

    /**
     * Метод, возвращающий айди чата
     *
     * @param update принимает апдейт от бота
     * @return возвращает строку с чат айди
     */
    public static String getChatIdFromUpdate(IUpdate update) {
        return update.getMessage().getChatId().toString();
    }

    /**
     * Метод для отправки фотографии с подписью
     *
     * @param update      апдейт от бота
     * @param nextProfile следующий профиль
     * @param message     само сообщение
     */
    public static void sendPhotoWithCaption(IUpdate update, Profile nextProfile, String message) {
        var chatId = getChatIdFromUpdate(update);

        if (nextProfile.ID != -1) {
            var photo = new InputFile(nextProfile.getPhotoLink());
            sendPhoto(chatId, photo, Keyboards.main.getTelegramKeyboard(), message);
            return;
        }

        sendMessageWithKeyboard(chatId, "Ты долистал анкеты до конца! Начинаем по второму кругу.", Keyboards.main.getTelegramKeyboard());

    }

    /**
     * Отправляет сообщение
     * @param chatId id чата
     * @param text текст
     */
    public static void sendMessage(String chatId, String text) {
        try {
            bot.execute(SendMessage.builder()
                    .chatId(chatId)
                    .text(text)
                    .build());
        }
        catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * Отправляет сообщение с клавиатурой
     * @param chatId id чата
     * @param text текст
     * @param replyMarkup клавиатура
     */
    public static void sendMessageWithKeyboard(String chatId, String text, ReplyKeyboardMarkup replyMarkup) {
        try {
            bot.execute(SendMessage.builder()
                    .chatId(chatId)
                    .text(text)
                    .replyMarkup(replyMarkup)
                    .build());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * Отправляет фото с подписью
     * @param chatId id чата
     * @param photo фото
     * @param replyMarkup клавиатура
     * @param caption подпись
     */
    public static void sendPhoto(String chatId, InputFile photo, ReplyKeyboardMarkup replyMarkup, String caption) {
        try {
            bot.execute(SendPhoto.builder()
                    .chatId(chatId)
                    .photo(photo)
                    .replyMarkup(replyMarkup)
                    .caption(caption)
                    .build()
            );
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
