package ru.urfu.discord;

import net.dv8tion.jda.api.JDA;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Класс, отправляющий сообщения в дискорд
 */

public class DiscordMessageSender {
    public static JDA api;

    /**
     * Отправляет сообщение с текстом в канал
     * @param chatId chatId куда отправляем
     * @param text что отправляем
     */
    public static void sendMessage(Long chatId, String text) {
        api.getPrivateChannelById(chatId).sendMessage(text).queue();
        //api.getTextChannelById(chatId).sendMessage(text).queue();
    }

    /**
     * Отправляем фото по URL
     * @param chatId chatId куда отправляем
     * @param text текст
     * @param urlStr URL фотографии
     */
    public static void sendPhoto(Long chatId, String text, String urlStr) {
        try {
            URL url = new URL(urlStr);
            BufferedImage img = ImageIO.read(url);
            File file = new File("temp.png");
            ImageIO.write(img, "png", file);
            api.getPrivateChannelById(chatId).sendMessage(text).addFile(file).queue();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
