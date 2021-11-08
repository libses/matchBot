package ru.urfu.bot;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.urfu.profile.ProfileData;

import java.io.*;
import java.util.List;
import java.util.Scanner;

/**
 * Класс с самим телеграм-ботом, необходимый для связи с telegram через API
 */

public class Bot extends TelegramLongPollingBot {

    private final String token;
    private final String userName;
    private final UpdateHandler updateHandler;
    public final ProfileData data;

    public final ReplyKeyboardMarkup defaultKeyboard = new ReplyKeyboardMarkup(
            List.of(new KeyboardRow(
                    List.of(new KeyboardButton("❤️"),
                            new KeyboardButton("\uD83D\uDC4E"))
            ))
            , true, false, false, " "
    );

    /**
     * Метод создаёт нового бота
     *
     * @return возвращает бота
     * @throws IOException бросает эксепшн при ошибке ввода
     */
    public static Bot create() throws IOException {
        String token;
        String userName;

        try (var reader = new FileReader("botConfig.txt"); var scanner = new Scanner(reader)) {
            token = scanner.nextLine().split("=")[1];
            userName = scanner.nextLine().split("=")[1];
            if (token.length() < 14) {
                throw new FileNotFoundException();
            }
            if (!userName.endsWith("bot")) {
                throw new FileNotFoundException();
            }

        } catch (Exception e) {
            new File("", "botConfig.txt");
            var writer = new FileWriter("botConfig.txt", false);
            var scanner = new Scanner(System.in);

            token = " ";
            while (token.length() < 14) {
                System.out.println("Введите корректный токен бота:");
                token = scanner.nextLine();
            }


            writer.write("botToken=" + token + '\n');

            userName = " ";
            while (!userName.endsWith("bot")) {
                System.out.println("Введите корректный username бота:");

                userName = scanner.nextLine();
            }
            writer.write("botUserName=" + userName);

            scanner.close();
            writer.close();
            System.out.println("Бот запущен, ждём сообщений.");
        }

        return new Bot(token, userName);
    }


    public Bot(String token, String userName) {
        this.token = token;
        this.userName = userName;
        data = new ProfileData();
        updateHandler = new UpdateHandler(data, this);
    }

    /**
     * Обрабатывает обновление
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.getMessage().hasText()) {
            try {
                updateHandler.handleText( update);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        if (update.getMessage().hasPhoto()) {
            try {
                updateHandler.handlePhoto( update);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public String getBotUsername() {
        return userName;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
