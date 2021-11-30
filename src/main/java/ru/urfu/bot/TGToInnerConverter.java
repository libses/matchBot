package ru.urfu.bot;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.ArrayList;
import java.util.List;

public class TGToInnerConverter {
    public static InnerUpdate Convert(Update update){
        return new InnerUpdate(Convert(update.getMessage()));
    }

    public static InnerMessage Convert(Message message) {
        var innerUser = Convert(message.getFrom());
        var innerPhotos = Convert(message.getPhoto());
        return new InnerMessage(innerUser, message.getText(), message.getChatId(), innerPhotos);
    }

    public static InnerUser Convert(User user) {
        return new InnerUser(user.getId(), user.getUserName());
    }

    public static List<IPhotoSize> Convert(List<PhotoSize> photoSizes) {
        var list = new ArrayList<IPhotoSize>();
        if (photoSizes != null) {
            for (PhotoSize photo : photoSizes) {
                list.add(Convert(photo));
            }
        }
        return list;
    }

    public static InnerPhoto Convert(PhotoSize photo) {
        return new InnerPhoto(photo.getFileId());
    }
}
