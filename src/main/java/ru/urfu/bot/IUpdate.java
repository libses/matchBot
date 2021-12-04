package ru.urfu.bot;

public interface IUpdate {

    IMessage getMessage();

    boolean isFromTelegram();
}
