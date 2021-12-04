package ru.urfu.bot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public class Keyboard implements IKeyboard {
    private final ReplyKeyboardMarkup telegramKeyboard;
    private final IDiscordKeyboard discordKeyboard;

    public Keyboard(ReplyKeyboardMarkup telegramKeyboard) {
        this.telegramKeyboard = telegramKeyboard;
        this.discordKeyboard = KeyboardTranslator.TranslateTelegramKeyboardForDiscord(telegramKeyboard);
    }

    @Override
    public ReplyKeyboardMarkup getTelegramKeyboard() {
        return telegramKeyboard;
    }

    @Override
    public IDiscordKeyboard getDiscordKeyboard() {
        return discordKeyboard;
    }

    @Override
    public String getCommand(String key) {
        return discordKeyboard.getCommand(key);
    }
}
