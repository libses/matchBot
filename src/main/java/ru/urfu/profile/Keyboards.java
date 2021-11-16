package ru.urfu.profile;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

public class Keyboards {

    public final ReplyKeyboardMarkup main = new ReplyKeyboardMarkup(
            List.of(new KeyboardRow(
                    List.of(new KeyboardButton("❤️"),
                            new KeyboardButton("\uD83D\uDC4E"),
                            new KeyboardButton("Еще"))
            ))
            , true, false, false, " "
    );

    public final ReplyKeyboardMarkup additionalMenu = new ReplyKeyboardMarkup(
            List.of(new KeyboardRow(
                            List.of(new KeyboardButton("Мои ❤️"),
                                    new KeyboardButton("Я понравился???"))),
                    new KeyboardRow(
                            List.of(new KeyboardButton("Взаимные \uD83D\uDC9E"),
                                    new KeyboardButton("Назад"))))
            , true, false, false, " "
    );

    public final ReplyKeyboardMarkup invalidCommand = ReplyKeyboardMarkup.builder()
            .keyboardRow(new KeyboardRow(List.of(new KeyboardButton("Ок, понял"))))
            .build();
}
