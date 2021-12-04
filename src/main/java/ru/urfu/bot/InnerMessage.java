package ru.urfu.bot;

import java.util.List;

public class InnerMessage implements IMessage {
    private IUser author;
    private String text;
    private Long chatId;
    private List<IPhotoSize> photos;

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
