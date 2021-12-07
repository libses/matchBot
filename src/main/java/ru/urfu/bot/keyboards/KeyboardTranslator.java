package ru.urfu.bot.keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.HashMap;

/**
 * Адаптируем телеграм клавиатуру для дискорда
 */
public class KeyboardTranslator {
    public static DiscordKeyboard TranslateTelegramKeyboardForDiscord(ReplyKeyboardMarkup tgKeyboard) {

        var index = 1;
        var result = new StringBuilder();
        var commands = new HashMap<String, String>();

        for (var row : tgKeyboard.getKeyboard()
        ) {
            for (var bottom : row
            ) {
                commands.put(String.valueOf(index), bottom.getText());
                result.append(index)
                        .append(".")
                        .append(bottom.getText())
                        .append(", ");
                index++;
            }
            result.append('\n');
        }

        return new DiscordKeyboard(result.substring(0, result.length() - 3), commands);
    }
}
