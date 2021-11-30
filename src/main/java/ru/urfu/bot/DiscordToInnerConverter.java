package ru.urfu.bot;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;

public class DiscordToInnerConverter {

    public static IUpdate Convert(MessageReceivedEvent event) {
        return new InnerUpdate(Convert(event.getMessage()), false);
    }

    public static IMessage Convert(Message message) {
        var user = new InnerUser(message.getTextChannel().getIdLong(), message.getAuthor().getName(), false);
        return new InnerMessage(user, message.getContentRaw(), message.getTextChannel().getIdLong(), Convert(message.getAttachments()));
    }

    public static List<IPhotoSize> Convert(List<Message.Attachment> photos) {
        var list = new ArrayList<IPhotoSize>();
        for (Message.Attachment att : photos) {
            if (att.isImage()) {
                list.add(Convert(att));
            }
        }
        return list;
    }

    public static IPhotoSize Convert(Message.Attachment photo) {
        if (photo.isImage()) {
            return new InnerPhoto(photo.getId());
        }
        return null;
    }
}
