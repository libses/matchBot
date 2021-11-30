package ru.urfu.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.urfu.profile.Profile;

public class TelegramMessageSender {
    public static Bot bot;
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
        try
        {
            var photo = new InputFile(nextProfile.getPhotoLink());
            sendPhoto(chatId, photo, Keyboards.main, message);
        }
        catch (Exception e)
        {
            sendMessage(chatId, "Анкеты кончились или произошла ошибка!", Keyboards.main);
        }
    }

    public static void sendMessage(String chatId, String text, ReplyKeyboardMarkup replyMarkup){
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
