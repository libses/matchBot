package ru.urfu.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface IUpdate {

    IMessage getMessage();

    boolean isFromTelegram();
}
