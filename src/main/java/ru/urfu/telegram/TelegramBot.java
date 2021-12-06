package ru.urfu.telegram;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.urfu.bot.UpdateHandler;

import java.io.IOException;

/**
 * Класс с самим телеграм-ботом, необходимый для связи с telegram через API
 */

public class TelegramBot extends TelegramLongPollingBot {

    private final String token;
    private final String userName;
    private final UpdateHandler updateHandler;

    /**
     * Метод создаёт нового бота
     *
     * @return возвращает бота
     * @throws IOException бросает эксепшн при ошибке ввода
     */
    public static TelegramBot create() throws Exception {
        String token = System.getenv("BOT_TOKEN");
        String userName = System.getenv("BOT_NAME");
        if (token == null || userName == null || !userName.endsWith("bot") || token.length() != 46) {
            throw new Exception("Проверьте перменные среды BOT_TOKEN и BOT_NAME");
        }


        var newBot = new TelegramBot(token, userName);
        TelegramMessageSender.bot = newBot;
        return newBot;
    }


    public TelegramBot(String token, String userName) {
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

        if (innerUpdate.hasLocation()) {
            updateHandler.handleLocation(innerUpdate);
        }

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
