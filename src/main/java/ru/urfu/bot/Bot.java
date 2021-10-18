package ru.urfu.bot;


import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.BotOptions;
import org.telegram.telegrambots.meta.generics.LongPollingBot;

import java.io.*;
import java.util.Scanner;

public class Bot implements LongPollingBot {

    private final String token;
    private final String userName;

    public Bot(String token, String userName) {
        this.token = token;
        this.userName = userName;
    }

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

    @Override
    public void onUpdateReceived(Update update) {

    }

    @Override
    public BotOptions getOptions() {
        return null;
    }

    @Override
    public void clearWebhook() throws TelegramApiRequestException {

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
