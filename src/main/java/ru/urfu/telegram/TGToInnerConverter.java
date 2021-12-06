package ru.urfu.telegram;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.urfu.bot.*;
import ru.urfu.bot.locations.Location;

import java.util.ArrayList;
import java.util.List;

public class TGToInnerConverter {
    public static InnerUpdate Convert(Update update) {
        if (update.getMessage().getLocation() == null) {
            return new InnerUpdate(Convert(update.getMessage()), true);
        }
        else {
            return new InnerUpdate(Convert(update.getMessage()), true, Convert(update.getMessage().getLocation()));
        }
    }

    public static Location Convert(org.telegram.telegrambots.meta.api.objects.Location location) {
        return new Location(location.getLongitude(), location.getLatitude());
    }

    public static InnerMessage Convert(Message message) {
        var innerUser = Convert(message.getFrom());
        var innerPhotos = Convert(message.getPhoto());
        return new InnerMessage(innerUser, message.getText(), message.getChatId(), innerPhotos);
    }

    public static InnerUser Convert(User user) {
        return new InnerUser(user.getId(), user.getUserName(), true);
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