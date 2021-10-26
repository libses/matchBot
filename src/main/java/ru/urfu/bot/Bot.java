package ru.urfu.bot;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.urfu.profile.ProfileData;

import java.io.*;
import java.util.Scanner;

/**
 * Класс с самим телеграм-ботом, необходимый для связи с telegram через API
 */

public class Bot extends TelegramLongPollingBot {

    private final String token;
    private final String userName;
    private final UpdateHandler updateHandler;
    public ProfileData data;

    /**
     * Метод создаёт нового бота
     * @return возвращает бота
     * @throws IOException бросает эксепшн при ошибке ввода
     */
    public static Bot create() throws IOException {
        String token;
        String userName;

        try {
            var reader = new FileReader("botConfig.txt");
            var scanner = new Scanner(reader);
            token = scanner.nextLine().split("=")[1];
            userName = scanner.nextLine().split("=")[1];

            scanner.close();
            reader.close();

        } catch (FileNotFoundException e) {
            new File("", "botConfig.txt");
            var writer = new FileWriter("botConfig.txt", true);
            var scanner = new Scanner(System.in);

            System.out.println("Введите токен бота:");

            token = scanner.nextLine();
            writer.write("botToken=" + token + '\n');

            System.out.println("Введите username бота:");

            userName = scanner.nextLine();
            writer.write("botUserName=" + userName);

            scanner.close();
            writer.close();
        }

        return new Bot(token, userName);
    }


    public Bot(String token, String userName) {
        this.token = token;
        this.userName = userName;
        data = new ProfileData();
        updateHandler = new UpdateHandler(data,this);
    }

    /**
     * Обрабатывает обновление
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.getMessage().hasText()) {
            try {
                updateHandler.handleText(this, update);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        if (update.getMessage().hasPhoto()) {
            try {
                updateHandler.handlePhoto(this, update);
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
