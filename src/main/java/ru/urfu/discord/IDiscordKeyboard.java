package ru.urfu.discord;

/**
 * Интерфейс клавиатуры дискорда
 */
public interface IDiscordKeyboard {
    String getKeyboard();

    String getCommand(String key);
}
