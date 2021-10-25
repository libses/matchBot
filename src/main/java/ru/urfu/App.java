package ru.urfu;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.urfu.bot.Bot;

public class App {
    public static void main(String[] args) {
        try {
            var bot = Bot.create();
            var telegramApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramApi.registerBot(bot);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
