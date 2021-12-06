package ru.urfu.bot;

import java.util.List;

/**
 * Класс сообщений, используемых внутри логики самого бота
 */

public class InnerMessage implements IMessage {
    private final IUser author;
    private final String text;
    private final Long chatId;
    private final List<IPhotoSize> photos;

    public InnerMessage(IUser user, String text, Long chatId, List<IPhotoSize> photos) {
        author = user;
        this.text = text;
        this.chatId = chatId;
        this.photos = photos;
    }

    public IUser getFrom() {
        return author;
    }

    public String getText() {
        return text;
    }

    public Long getChatId() {
        return chatId;
    }

    public List<IPhotoSize> getPhoto() {
        return photos;
    }

    public boolean hasText() {
        if (text == null) {
            return false;
        }
        return !text.isEmpty();
    }

    public boolean hasPhoto() {
        return !photos.isEmpty();
    }
}
