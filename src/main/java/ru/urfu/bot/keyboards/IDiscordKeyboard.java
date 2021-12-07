package ru.urfu.bot.keyboards;

/**
 * Интерфейс клавиатуры дискорда
 */
public interface IDiscordKeyboard {
    String getKeyboard();

    String getCommand(String key);
}
