package ru.urfu.bot;

public interface IDiscordKeyboard {
    String getKeyboard();

    String getCommand(String key);
}
