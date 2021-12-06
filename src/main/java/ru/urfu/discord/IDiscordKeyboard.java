package ru.urfu.discord;

public interface IDiscordKeyboard {
    String getKeyboard();

    String getCommand(String key);
}
