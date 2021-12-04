package ru.urfu.bot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

/**
 * Тут храним клавиатуры, используемые в боте
 */
public class Keyboards {
    /**
     * Клавиатура основного меню, где мы ставим лайки, дизлайки и можем перейти в доп меню
     */
    public static final IKeyboard main = new Keyboard(
            new ReplyKeyboardMarkup(
                    List.of(new KeyboardRow(
                            List.of(new KeyboardButton("❤️"),
                                    new KeyboardButton("\uD83D\uDC4E"),
                                    new KeyboardButton("Еще"))
                    ))
                    , true, false, false, " "
            ));

    /**
     * Клавиатура доп меню, где мы можем посмотреть свединья о лайках
     */
    public static final IKeyboard additionalMenu = new Keyboard(
            new ReplyKeyboardMarkup(
                    List.of(new KeyboardRow(
                                    List.of(new KeyboardButton("Мои ❤️"),
                                            new KeyboardButton("Я понравился???"))),
                            new KeyboardRow(
                                    List.of(new KeyboardButton("Взаимные \uD83D\uDC9E"),
                                            new KeyboardButton("Назад"))))
                    , true, false, false, " "
            ));

    /**
     * Кнопка "ok"
     */
    public static final IKeyboard invalidCommand = new Keyboard(
            ReplyKeyboardMarkup.builder()
                    .keyboardRow(new KeyboardRow(List.of(new KeyboardButton("Ок, понял"))))
                    .build());

    /**
     * Клавиатура выбора пола при регистрации
     */
    public static final IKeyboard genders = new Keyboard(
            ReplyKeyboardMarkup.builder()
                    .keyboardRow(new KeyboardRow(List.of(
                            new KeyboardButton("Мужской\uD83D\uDE4B\u200D♂️"),
                            new KeyboardButton("Женский\uD83D\uDE4B\u200D♀️"),
                            new KeyboardButton("Non-Binary\uD83C\uDFF3️\u200D⚧️"))))
                    .resizeKeyboard(true)
                    .oneTimeKeyboard(true)
                    .build());

    public static final IKeyboard go = new Keyboard(
            ReplyKeyboardMarkup.builder()
                    .keyboardRow(new KeyboardRow(List.of(
                            new KeyboardButton("Поехали!\uD83D\uDE40"))))
                    .resizeKeyboard(true)
                    .oneTimeKeyboard(true)
                    .build());


}
