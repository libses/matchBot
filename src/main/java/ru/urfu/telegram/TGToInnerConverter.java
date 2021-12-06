package ru.urfu.telegram;

import org.telegram.telegrambots.meta.api.objects.*;
import ru.urfu.bot.*;
import ru.urfu.bot.locations.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс,конвертирующий апдейты TG во внутренние апдейты
 */
public class TGToInnerConverter {
    /**
     * Конвертирует update телеграмма в InnerUpdate
     * @param update апдейт телеграмма
     * @return внутренний InnerUpdate
     */
    public static InnerUpdate Convert(Update update) {
        if (update.getMessage().getLocation() == null) {
            return new InnerUpdate(Convert(update.getMessage()), true);
        }
        else {
            return new InnerUpdate(Convert(update.getMessage()), true, Convert(update.getMessage().getLocation()));
        }
    }

    /**
     * Конвертирует локацию из телеграммовской в внутреннюю
     * @param location локация телеграмма
     * @return внутреннюю локацию с округлением
     */
    public static Location Convert(org.telegram.telegrambots.meta.api.objects.Location location) {
        return new Location(location.getLongitude(), location.getLatitude());
    }

    /**
     * Конвертирует сообщение телеграмма во внутреннее сообщение
     * @param message сообщение от телеграмма
     * @return возвращает внутреннее сообщение
     */
    public static InnerMessage Convert(Message message) {
        var innerUser = Convert(message.getFrom());
        var innerPhotos = Convert(message.getPhoto());
        return new InnerMessage(innerUser, message.getText(), message.getChatId(), innerPhotos);
    }

    /**
     * Конвертирует пользователя телеграмма во внутреннего юзера
     * @param user юзер телеграмма
     * @return внутренний юзер
     */
    public static InnerUser Convert(User user) {
        return new InnerUser(user.getId(), user.getUserName(), true);
    }

    /**
     * Конвертирует фотографии из телеграмма во внутренние
     * @param photoSizes фото из телеграмма
     * @return внутренние фото
     */
    public static List<IPhotoSize> Convert(List<PhotoSize> photoSizes) {
        var list = new ArrayList<IPhotoSize>();
        if (photoSizes != null) {
            for (PhotoSize photo : photoSizes) {
                list.add(Convert(photo));
            }
        }
        return list;
    }

    /**
     * Конвертирует фото из телеграмма во внутреннее
     * @param photo фото из телеграмма
     * @return внутреннее фото
     */
    public static InnerPhoto Convert(PhotoSize photo) {
        return new InnerPhoto(photo.getFileId());
    }
}
