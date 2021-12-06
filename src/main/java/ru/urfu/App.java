package ru.urfu;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.urfu.discord.DiscordBot;
import ru.urfu.telegram.TelegramBot;

/**
 * Класс самого приложения. Запускает самого бота и тгАПИ
 */
public class App {
    public static void main(String[] args) {
        try {
            var telegramBot = TelegramBot.create();
            var telegramApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramApi.registerBot(telegramBot);
            DiscordBot.main(new String[] {});
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
