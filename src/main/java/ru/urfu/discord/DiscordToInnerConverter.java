package ru.urfu.discord;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import ru.urfu.bot.*;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс, конвертирующий дискордовские сущности во внутренние сущности программы
 */

public class DiscordToInnerConverter {

    /**
     * Конвертирует ивент в IUpdate
     * @param event ивент
     * @return возвращает InnerUpdate
     */
    public static IUpdate Convert(MessageReceivedEvent event) {
        return new InnerUpdate(Convert(event.getMessage()), UpdateSource.Discord);
    }

    /**
     * Конвертирует message в IMessage
     * @param message сообщение
     * @return возвращает InnerMessage
     */
    public static IMessage Convert(Message message) {
        var user = new InnerUser(message.getPrivateChannel().getIdLong(), message.getAuthor().getName(), UpdateSource.Discord);
        return new InnerMessage(user, message.getContentRaw(), message.getPrivateChannel().getIdLong(), Convert(message.getAttachments()));
    }

    /**
     * конвертирует фото в List<IPhotoSize>
     * @param photos фото дискорда
     * @return возвращает внутренне понятную структуру
     */
    public static List<IPhotoSize> Convert(List<Message.Attachment> photos) {
        var list = new ArrayList<IPhotoSize>();
        for (Message.Attachment att : photos) {
            if (att.isImage()) {
                list.add(Convert(att));
            }
        }
        return list;
    }

    /**
     * Конвертирует фото дискорда во внутреннее
     * @param photo фото дискорда
     * @return внутреннее фото
     */
    public static IPhotoSize Convert(Message.Attachment photo) {
        if (photo.isImage()) {
            return new InnerPhoto(photo.getUrl());
        }
        return null;
    }
}
