package ru.urfu.discord;

import net.dv8tion.jda.api.JDA;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class DiscordMessageSender {
    public static JDA api;

    public static void sendMessage(Long chatId, String text) {
        api.getTextChannelById(chatId).sendMessage(text).queue();
    }

    public static void sendPhoto(Long chatId, String text, String urlStr) {
        try {
            URL url = new URL(urlStr);
            BufferedImage img = ImageIO.read(url);
            File file = new File("temp.png");
            ImageIO.write(img, "png", file);
            api.getTextChannelById(chatId).sendMessage(text).addFile(file).queue();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}