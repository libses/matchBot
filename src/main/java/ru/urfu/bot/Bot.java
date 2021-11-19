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

        try {
            var tokenSystem = System.getenv("TOKEN");
            var nameSystem = System.getenv("NAME");
            if (tokenSystem.isEmpty() || nameSystem.isEmpty()) {
                throw new Exception();
            }
            token = tokenSystem;
            userName = nameSystem;
        }
        catch (Exception e1) {
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
        }

        return new Bot(token, userName);
    }


    public Bot(String token, String userName) {
        this.token = token;
        this.userName = userName;
        updateHandler = new UpdateHandler(this);
    }

    /**
     * Обрабатывает обновление
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.getMessage().hasText()) {
            try {
                updateHandler.handleText(update);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        if (update.getMessage().hasPhoto()) {
            try {
                updateHandler.handlePhoto(update);
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
