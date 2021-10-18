package ru.urfu.bot;


import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendPhoto;

import java.util.List;
import java.util.Scanner;

public class Bot implements Runnable {
    TelegramBot bot = new TelegramBot("2058884544:AAF2Ayxfm3SeZBrSR5sOebOCoeqwlo3FgC8");
    final int limit = 1000;
    int offset = 0;
    final int timeout = 1000;
    GetUpdates getUpdates = new GetUpdates().limit(limit).offset(offset).timeout(timeout);
    List<Update> updates;
    private volatile boolean updating = true;


    public void updateHandler() {
        while (updating) {
            for (var update : updates
            ) {
                if (update.message().photo().length != 0) {
                    var send
                            = new SendPhoto(update.message().chat().id(), update.message().photo()[0].fileId());
                    bot.execute(send);
                }
                offset += updates.size();
                updates = bot.execute(getUpdates).updates();
            }
        }

    }

    public void run() {
        updates = bot.execute(getUpdates).updates();
        offset = updates.stream().map(Update::updateId).max(Integer::compareTo).get() + 1;
        new Thread(this::updateHandler).start();
    }

    public void shutdown() {
        updating = false;
        bot.shutdown();
    }

    public static void main(String[] args) {
        Bot bot = new Bot();
        bot.run();

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        bot.shutdown();
    }
}
