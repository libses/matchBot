package ru.urfu.bot;

import java.util.List;

public interface IMessage {

    IUser getFrom();

    String getText();

    Long getChatId();

    List<IPhotoSize> getPhoto();

    boolean hasText();

    boolean hasPhoto();
}
