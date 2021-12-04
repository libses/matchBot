package ru.urfu.bot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public interface IKeyboard {
    ReplyKeyboardMarkup getTelegramKeyboard();

    IDiscordKeyboard getDiscordKeyboard();

    String getCommand(String key);
}
