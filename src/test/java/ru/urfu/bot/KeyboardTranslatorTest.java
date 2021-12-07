package ru.urfu.bot;

import org.junit.Test;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.urfu.bot.keyboards.KeyboardTranslator;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/***
 * Проверяем правильность трансляции
 */
public class KeyboardTranslatorTest{

    /**
     * Тест, проверяющий что клавиатура телеграмма грамотно конвертируюется в дискордовскую
     */
    @Test
    public void testTranslateTelegramKeyboardForDiscord_test1() {
        var tgKeyboard = ReplyKeyboardMarkup.builder()
                .keyboardRow(new KeyboardRow(List.of(
                        new KeyboardButton("Поехали!\uD83D\uDE40"))))
                .resizeKeyboard(true)
                .oneTimeKeyboard(true)
                .build();

        var result = KeyboardTranslator.TranslateTelegramKeyboardForDiscord(tgKeyboard).getKeyboard();
        var expected = "1.Поехали!\uD83D\uDE40";

        assertThat(result).isEqualTo(expected);
    }

    /**
     * Тест, проверяющий что клавиатура телеграмма грамотно конвертируюется в дискордовскую
     */
    @Test
    public void testTranslateTelegramKeyboardForDiscord_test2() {
        var tgKeyboard =  new ReplyKeyboardMarkup(
                List.of(new KeyboardRow(
                                List.of(new KeyboardButton("Мои ❤️"),
                                        new KeyboardButton("Я понравился???"))),
                        new KeyboardRow(
                                List.of(new KeyboardButton("Взаимные \uD83D\uDC9E"),
                                        new KeyboardButton("Назад"))))
                , true, false, false, " "
        );

        var result = KeyboardTranslator.TranslateTelegramKeyboardForDiscord(tgKeyboard).getKeyboard();
        var expected = "1.Мои ❤️, 2.Я понравился???, \n3.Взаимные \uD83D\uDC9E, 4.Назад";

        assertThat(result).isEqualTo(expected);
    }
}
