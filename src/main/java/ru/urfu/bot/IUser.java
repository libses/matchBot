package ru.urfu.bot;

public interface IUser {

    Long getId();

    String getUserName();

    boolean isTelegramUser();
}
