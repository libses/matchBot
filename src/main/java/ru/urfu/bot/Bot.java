package ru.urfu.bot;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.*;
import java.util.Scanner;

/**
 * Класс с самим телеграм-ботом, необходимый для связи с telegram через API
 */

public class Bot extends TelegramLongPollingBot {

    private final String token;
    private final String userName;
    private final UpdateHandler updateHandler;

    /**
     * Метод создаёт нового бота
     *
     * @return возвращает бота
     * @throws IOException бросает эксепшн при ошибке ввода
     */
    public static Bot create() throws Exception {
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

        } catch (Exception e2) {
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

        var newBot = new Bot(token, userName);
        TelegramMessageSender.bot = newBot;
        return newBot;
    }


    public Bot(String token, String userName) {
        this.token = token;
        this.userName = userName;
        updateHandler = new UpdateHandler();
    }

    /**
     * Обрабатывает обновление
     */
    @Override
    public void onUpdateReceived(Update update) {
        var innerUpdate = TGToInnerConverter.Convert(update);
        if (innerUpdate.getMessage().hasText()) {
            try {
                updateHandler.handleText(innerUpdate);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        if (innerUpdate.getMessage().hasPhoto()) {
            try {
                updateHandler.handlePhoto(innerUpdate);
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
