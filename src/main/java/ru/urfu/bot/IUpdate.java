package ru.urfu.bot;

import ru.urfu.bot.locations.ILocation;

public interface IUpdate {

    IMessage getMessage();

    boolean isFromTelegram();

    boolean hasLocation();

    ILocation getLocation();
}
