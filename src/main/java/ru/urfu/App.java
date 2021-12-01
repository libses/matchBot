package ru.urfu;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.urfu.bot.TelegramBot;

/**
 * Класс самого приложения. Запускает самого бота и тгАПИ
 */
public class App {
    public static void main(String[] args) {
        try {
            var bot = TelegramBot.create();
            var telegramApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramApi.registerBot(bot);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
