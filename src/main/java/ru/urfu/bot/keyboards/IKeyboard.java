package ru.urfu.bot.keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import ru.urfu.discord.IDiscordKeyboard;

public interface IKeyboard {
    ReplyKeyboardMarkup getTelegramKeyboard();

    IDiscordKeyboard getDiscordKeyboard();

    String getCommand(String key);
}
