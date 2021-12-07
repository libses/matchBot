package ru.urfu.bot;

import java.util.List;

/**
 * Интерфейс сообщений с необходимыми для работы методами
 */
public interface IMessage {

    IUser getFrom();

    String getText();

    Long getChatId();

    List<IPhotoSize> getPhoto();

    boolean hasText();

    boolean hasPhoto();
}
