package ru.urfu.discord;

import java.util.Map;

public class DiscordKeyboard implements IDiscordKeyboard {
    private final String keyboard;
    private final Map<String, String> commands;

    public DiscordKeyboard(String keyboard, Map<String, String> commands) {
        this.keyboard = keyboard;
        this.commands = commands;
    }

    public String getCommand(String key) {
        return commands.get(key);
    }

    public String getKeyboard() {
        return keyboard;
    }
}
