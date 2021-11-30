package ru.urfu.bot;

import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import ru.urfu.profile.Profile;

public class MessageSender {
    public static void sendPhotoWithCaption(IUpdate update, Profile nextProfile, String message) {
        TelegramMessageSender.sendPhotoWithCaption(update, nextProfile, message);
    }
    public static void sendMessage(String chatId, String text, ReplyKeyboardMarkup replyMarkup) {
        TelegramMessageSender.sendMessage(chatId, text, replyMarkup);
    }
    public static void sendPhoto(String chatId, InputFile photo, ReplyKeyboardMarkup replyMarkup, String caption) {
        TelegramMessageSender.sendPhoto(chatId, photo, replyMarkup, caption);
    }
}
